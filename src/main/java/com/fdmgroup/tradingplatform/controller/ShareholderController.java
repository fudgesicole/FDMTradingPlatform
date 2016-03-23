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
import com.fdmgroup.tradingplatform.model.dao.ITradeDAO;
import com.fdmgroup.tradingplatform.model.dao.IUserDAO;
import com.fdmgroup.tradingplatform.model.entity.Company;
import com.fdmgroup.tradingplatform.model.entity.Request;
import com.fdmgroup.tradingplatform.model.entity.Share;
import com.fdmgroup.tradingplatform.model.entity.Trade;
import com.fdmgroup.tradingplatform.model.entity.User;

/**
 * Request mappings for shareholder actions. URL mappings in this class
 * are only accessible through the filter when the loggedInUser is 
 * has the 'shareholder' role.
 */
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
	
	@Autowired
	private ITradeDAO tradeDAO;
	
	//display the page for purchasing shares.
	@RequestMapping(value = "/buyShares", method = {RequestMethod.POST, RequestMethod.GET})
	public String index(Model model){
		List<Company> companies = companyDAO.readAll();
		if(companies == null){
			model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
		}
		model.addAttribute("companies", companies);
		//This request attribute's fields will be populated by spring forms for 
		//new request creation or company editing.
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
		//sellRequest attribute because the user is given the option to 
		//sell the shares they own on the portfolio page.
		model.addAttribute("sellRequest", (Request) context.getBean("request"));
		return "portfolio";
	}

	@RequestMapping(value = "/buy", method = {RequestMethod.POST, RequestMethod.GET})
	public String buy(Request buyRequest,  @RequestParam Integer companyId, BindingResult br, Model model, @ModelAttribute("loggedInUser") User loggedInUser){
		if (br.hasErrors()) {
			model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
			return "forward:/buyShares";
		}
		buyRequest.setCompany(companyDAO.read(companyId));
		//Request date is the current date
		buyRequest.setRequestDate(new Date(Calendar.getInstance().getTime().getTime()));
		//Request shareholder is the currently logged in user from the session
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

	@RequestMapping(value = "/sell", method = {RequestMethod.POST, RequestMethod.GET})
	public String sell(Request sellRequest,  @RequestParam Integer companyId, BindingResult br, Model model, @ModelAttribute("loggedInUser") User loggedInUser){
		if (br.hasErrors()) {
			model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
			return "forward:/portfolio";
		}
		sellRequest.setCompany(companyDAO.read(companyId));
		sellRequest.setRequestDate(new Date(Calendar.getInstance().getTime().getTime()));
		sellRequest.setShareholder(userDAO.read(loggedInUser.getId()));
		sellRequest.setSharesFilled(0);
		sellRequest.setStatus("ACTIVE");
		sellRequest.setType("SELL");
		if(!requestDAO.create(sellRequest)){
			model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
			return "forward:/portfolio";
		}
		
		model.addAttribute("successMsg", "Request sent!");
		return "forward:/portfolio";
	}

	@RequestMapping(value = "/requests", method = {RequestMethod.POST, RequestMethod.GET})
	public String requests(Model model, @ModelAttribute("loggedInUser") User loggedInUser){
		//display only the active requests for the logged in user from the session
		List<Request> requests = requestDAO.findActiveRequestsByUser(loggedInUser);
		if(requests == null){
			model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
		}
		model.addAttribute("requests", requests);
		return "outstandingTradeRequests";
	}

	@RequestMapping(value = "/tradeHistory", method = {RequestMethod.POST, RequestMethod.GET})
	public String tradeHistory(Model model, @ModelAttribute("loggedInUser") User loggedInUser){
		List<Trade> trades = tradeDAO.findTradesByUser(loggedInUser);
		if(trades == null){
			model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
		}
		model.addAttribute("trades", trades);
		return "tradeHistory";
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

	public ITradeDAO getTradeDAO() {
		return tradeDAO;
	}

	public void setTradeDAO(ITradeDAO tradeDAO) {
		this.tradeDAO = tradeDAO;
	}
	
}
