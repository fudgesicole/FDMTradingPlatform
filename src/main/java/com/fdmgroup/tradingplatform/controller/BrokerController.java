package com.fdmgroup.tradingplatform.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fdmgroup.tradingplatform.model.dao.ICompanyDAO;
import com.fdmgroup.tradingplatform.model.dao.IRequestDAO;
import com.fdmgroup.tradingplatform.model.dao.IRoleDAO;
import com.fdmgroup.tradingplatform.model.dao.IUserDAO;
import com.fdmgroup.tradingplatform.model.entity.AuthorizedShare;
import com.fdmgroup.tradingplatform.model.entity.Company;
import com.fdmgroup.tradingplatform.model.entity.Request;
import com.fdmgroup.tradingplatform.model.entity.Role;
import com.fdmgroup.tradingplatform.model.entity.User;

@Controller
@SessionAttributes(value = {"loggedInUser"}, types = {User.class})
public class BrokerController {
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private ICompanyDAO companyDAO;

	@Autowired
	private IUserDAO userDAO;

	@Autowired
	private IRoleDAO roleDAO;

	@Autowired
	private IRequestDAO requestDAO;

	@RequestMapping(value = "/brokerCompanyList", method = {RequestMethod.POST, RequestMethod.GET})
	public String brokerCompanyList(Model model){
		List<Company> companies = companyDAO.readAll();
		if(companies == null){
			model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
		}
		model.addAttribute("companies", companies);
		model.addAttribute("companyAttribute", (Company) context.getBean("company"));
		return "brokerCompanyList";
	}
	
	@RequestMapping(value="/brokerUpdateCompany", method = {RequestMethod.GET, RequestMethod.POST})
	public String brokerUpdateCompany(Model model, Company companyAttribute, BindingResult br){
		if (br.hasErrors()) {
			model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
			return "forward:/brokerCompanyList";
		}
		Company updatedCompany = companyDAO.read(companyAttribute.getId());
		if(updatedCompany == null){
			model.addAttribute("errMsg", "Company not found!");
			return "forward:/brokerCompanyList";
		}
		
		updatedCompany.setName(companyAttribute.getName());
		if(companyDAO.update(updatedCompany) == null){
			model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
			return "forward:/brokerCompanyList";
		}
		model.addAttribute("successMsg", "Company created successfully!");
		return "forward:/brokerCompanyList";
	}

	@RequestMapping(value="/brokerDeleteCompany", method = {RequestMethod.GET, RequestMethod.POST})
	public String brokerDeleteCompany(Model model, Company companyAttribute, BindingResult br){
		if (br.hasErrors()) {
			model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
			return "forward:/brokerCompanyList";
		}
		Company targetCompany = companyDAO.read(companyAttribute.getId());
		if(targetCompany == null){
			model.addAttribute("errMsg", "Company not found!");
			return "forward:/brokerCompanyList";
		}
		
		if(!companyDAO.delete(targetCompany)){
			model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
			return "forward:/brokerCompanyList";
		}
		model.addAttribute("successMsg", "Company deleted successfully!");
		return "forward:/brokerCompanyList";
	}
	
	@RequestMapping(value="/brokerAddCompany", method = {RequestMethod.GET, RequestMethod.POST})
	public String brokerAddCompany(Model model, Company companyAttribute, @RequestParam Integer initialAuthorizedShareCount, BindingResult br){
		if (br.hasErrors()) {
			model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
			return "forward:/userList";
		}
		AuthorizedShare initialAuthorizedShares = new AuthorizedShare();
		List<AuthorizedShare> initialAuthorizedSharesList = new ArrayList<AuthorizedShare>();
		initialAuthorizedShares.setCompany(companyAttribute);
		initialAuthorizedShares.setAuthorized(initialAuthorizedShareCount);
		initialAuthorizedShares.setTimeStart(new Date(Calendar.getInstance()
				.getTime().getTime()));
		initialAuthorizedSharesList.add(initialAuthorizedShares);
		companyAttribute.setAuthorizedShares(initialAuthorizedSharesList);
		
		if(companyDAO.findByName(companyAttribute.getName()) != null){
			model.addAttribute("errMsg", "A company named "+companyAttribute.getName()+" already exists.");
			return "forward:/brokerCompanyList";
		}
		
		if (!companyDAO.create(companyAttribute)) {
			model.addAttribute("errMsg", "Failure creating new company.");
			return "forward:/brokerCompanyList";
		}

		// Need to create a new User with the company role for the new company
		User newCompanyUser = new User();
		newCompanyUser.setUserName(companyAttribute.getName());
		newCompanyUser.setPassWord(companyAttribute.getName());
		newCompanyUser.setFirstName(companyAttribute.getName());
		newCompanyUser.setLastName(companyAttribute.getName());
		List<Role> newCompanyUserRoles = new ArrayList<Role>();
		Role companyRole = roleDAO.findByName("company");
		if(companyRole == null){
			companyRole = new Role();
			companyRole.setName("company");
			if(!roleDAO.create(companyRole)){
				model.addAttribute("errMsg", "Failure creating new company.");
				return "forward:/brokerCompanyList";
			}
		}
		newCompanyUserRoles.add(companyRole);
		newCompanyUser.setRoles(newCompanyUserRoles);
		if(!userDAO.create(newCompanyUser)){
			model.addAttribute("errMsg", "Failure creating user associated with new company");
			return "forward:/brokerCompanyList";
		}
		// Need to create a new request to sell the new company's initial
		// shares, if any:
		if (initialAuthorizedShareCount != 0) {
			Request newCompanySellRequest = new Request();
			newCompanySellRequest.setCompany(companyAttribute);
			newCompanySellRequest.setMinShares(0);
			newCompanySellRequest.setRequestDate(new Date(Calendar
					.getInstance().getTime().getTime()));
			newCompanySellRequest.setShareholder(newCompanyUser);
			newCompanySellRequest.setShareCount(initialAuthorizedShareCount);
			newCompanySellRequest.setSharesFilled(0);
			newCompanySellRequest.setStatus("ACTIVE");
			newCompanySellRequest.setTimeInForce("GOOD UNTIL CANCELLED");
			newCompanySellRequest.setType("SELL");
			if (!requestDAO.create(newCompanySellRequest)){
				model.addAttribute("errMsg", "Failure creating request to sell company initial shares.");
				return "forward:/brokerCompanyList";
			}
		}
		model.addAttribute("successMsg", "Company created successfully!");
		return "forward:/brokerCompanyList";
	}

	@RequestMapping(value = "/companyExists", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody boolean companyExists(String name) {
		return (companyDAO.findByName(name) != null);
	}
	
	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}

	public ICompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	public void setCompanyDAO(ICompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	public IRoleDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(IRoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	public IUserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public IRequestDAO getRequestDAO() {
		return requestDAO;
	}

	public void setRequestDAO(IRequestDAO requestDAO) {
		this.requestDAO = requestDAO;
	}
}
