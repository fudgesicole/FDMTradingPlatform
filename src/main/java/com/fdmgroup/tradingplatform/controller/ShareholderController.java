package com.fdmgroup.tradingplatform.controller;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fdmgroup.tradingplatform.model.dao.ICompanyDAO;
import com.fdmgroup.tradingplatform.model.dao.IRequestDAO;
import com.fdmgroup.tradingplatform.model.dao.IUserDAO;
import com.fdmgroup.tradingplatform.model.entity.Company;
import com.fdmgroup.tradingplatform.model.entity.Request;
import com.fdmgroup.tradingplatform.model.entity.Share;
import com.fdmgroup.tradingplatform.model.entity.User;

@Controller
@SessionAttributes(value = {"loggedInUser"}, types = {User.class})
public class ShareholderController {

	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private IUserDAO userDAO;

	@Autowired
	private ICompanyDAO companyDAO;

	@Autowired
	private IRequestDAO requestDAO;
	
	@RequestMapping(value = "/buyShares", method = {RequestMethod.POST, RequestMethod.GET})
	public String index(Model model){
		List<Company> companies = companyDAO.readAll();
		if(companies == null){
			model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
		}
		model.addAttribute("companies", companies);
		model.addAttribute("buyRequest", (Request) context.getBean("request"));
		return "buyShares";
	}

	@RequestMapping(value = "/portfolio", method = {RequestMethod.POST, RequestMethod.GET})
	public String portfolio(Model model, @ModelAttribute("loggedInUser") User loggedInUser){
		List<Share> shares = userDAO.getShareholderShares(loggedInUser);
		if(shares == null){
			model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
		}
		model.addAttribute("shares", shares);
		return "portfolio2";
	}

	@RequestMapping(value = "/buy", method = {RequestMethod.POST, RequestMethod.GET})
	public String buy(Request buyRequest,  @RequestParam Integer companyId, BindingResult br, Model model, @ModelAttribute("loggedInUser") User loggedInUser){
		if (br.hasErrors()) {
			model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
			return "forward:/buyShares";
		}
		buyRequest.setCompany(companyDAO.read(companyId));
		buyRequest.setRequestDate(new Date(Calendar.getInstance().getTime().getTime()));
		buyRequest.setShareholder(userDAO.read(loggedInUser.getId()));
		buyRequest.setSharesFilled(0);
		buyRequest.setStatus("ACTIVE");
		buyRequest.setType("BUY");
		if(!requestDAO.create(buyRequest)){
			model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
			return "forward:/buyShares";
		}
		
		model.addAttribute("successMsg", "Request sent!");
		return "forward:/buyShares";
	}

	public IUserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public ICompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	public void setCompanyDAO(ICompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	public IRequestDAO getRequestDAO() {
		return requestDAO;
	}

	public void setRequestDAO(IRequestDAO requestDAO) {
		this.requestDAO = requestDAO;
	}

	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}
	
}
