package com.fdmgroup.tradingplatform.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import com.fdmgroup.tradingplatform.model.dao.IRoleDAO;
import com.fdmgroup.tradingplatform.model.dao.IUserDAO;
import com.fdmgroup.tradingplatform.model.entity.Role;
import com.fdmgroup.tradingplatform.model.entity.User;

@Controller
@SessionAttributes(value = { "loggedInUser" }, types = { User.class })
public class AuthenticationController {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private IUserDAO userDAO;

	@Autowired
	private IRoleDAO roleDAO;

	@RequestMapping(value = "/", method = { RequestMethod.POST, RequestMethod.GET })
	public String index(Model model) {
		model.addAttribute("user", (User) context.getBean("user"));
		return "index";
	}

	@RequestMapping(value="/logout", method = {RequestMethod.GET, RequestMethod.POST})
	public String logout(Model model, User user, WebRequest request, SessionStatus status){
		status.setComplete();
		request.removeAttribute("loggedInUser", WebRequest.SCOPE_SESSION);
		model.addAttribute("successMsg", "You have been logged out.");
		return "forward:/";
	}

	@RequestMapping(value="/dashboard", method = {RequestMethod.GET, RequestMethod.POST})
	public String dashboard(Model model, @ModelAttribute("loggedInUser") User loggedInUser){
		for(Role role : loggedInUser.getRoles()){
			if(role.getName().equals("admin"))
				return "redirect:/userList";
			if(role.getName().equals("shareholder"))
				return "redirect:/portfolio";
			if(role.getName().equals("broker"))
				return "redirect:/brokerCompanyList";
		}
		model.addAttribute("errMsg", "An error occurred while displaying dashboard. Please try again. If the problem persists, contact technical support.");
		return "forward:/";
	}
	

	@RequestMapping(value = "/register", method = { RequestMethod.POST, RequestMethod.GET })
	public String registerUser(Model model, User user, BindingResult br, @RequestParam List<String> roleNames) {
		if (br.hasErrors()) {
			model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
			return "redirect:/";
		}
		User existingUser = userDAO.findByUserName(user.getUserName());

		if (existingUser != null) {
			model.addAttribute("errMsg", "Username is already taken.");
			return "forward:/";
		}
		List<Role> roles = new ArrayList<Role>();

		for (String name : roleNames) {
			Role foundRole = roleDAO.findByName(name);

			if (foundRole == null) {
				foundRole = new Role();
				foundRole.setName(name);
				if (!roleDAO.create(foundRole)) {
					model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
					return "forward:/";
				}
			}
			roles.add(roleDAO.findByName(name));
		}
		user.setRoles(roles);

		if (!userDAO.create(user)) {
			model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
			return "forward:/";
		}
		model.addAttribute("loggedInUser", userDAO.read(user.getId()));
		model.addAttribute("successMsg", "You've been successfully registered!");
		return "forward:/";
	}
	
	@RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET })
	public String login(Model model, User userDetails, BindingResult br) {
		if (br.hasErrors()) {
			model.addAttribute("errMsg", "An error occurred while logging in. Please try again.");
			return "forward:/";
		}
		User foundUser = userDAO.findByUserName(userDetails.getUserName());
		if(foundUser == null){
			model.addAttribute("errMsg", "An error occurred while logging in. Please try again.");
			return "forward:/";
		}
		if(foundUser.getPassWord().equals(userDetails.getPassWord())){
			model.addAttribute("loggedInUser", foundUser);
			return "forward:/dashboard";
		}
		else{
			model.addAttribute("errMsg", "Invalid username/password. Please try again.");
			return "forward:/";
		}
	}
	
	@RequestMapping(value = "/userExists", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody boolean userExists(String userName) {
		return (userDAO.findByUserName(userName) != null);
	}

	@RequestMapping(value = "/testLogin", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody boolean testLogin(String userName, String passWord) {
		User foundUser = userDAO.findByUserName(userName);
		if(foundUser == null){
			return false;
		}
		return foundUser.getPassWord().equals(passWord);
	}

	public IUserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public IRoleDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(IRoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}

}
