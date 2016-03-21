package com.fdmgroup.tradingplatform.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fdmgroup.tradingplatform.model.dao.IRoleDAO;
import com.fdmgroup.tradingplatform.model.dao.IUserDAO;
import com.fdmgroup.tradingplatform.model.entity.Role;
import com.fdmgroup.tradingplatform.model.entity.User;

@Controller
@SessionAttributes(value = { "loggedInUser" }, types = { User.class })
public class AdminController {
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private IUserDAO userDAO;

	@Autowired
	private IRoleDAO roleDAO;
	
	@RequestMapping(value="/userList", method = {RequestMethod.GET, RequestMethod.POST})
	public String userList(Model model){
		List<User> users = userDAO.readAll();
		if(users == null || users.size() == 0){
			model.addAttribute("errMsg", "An error while searching for users. Please try again. If the problem persists, contact technical support.");
			return "userList";
		}
		model.addAttribute("users", users);
		model.addAttribute("user", (User) context.getBean("user"));
		return "userList";
	}

	@RequestMapping(value="/adminAddUser", method = {RequestMethod.GET, RequestMethod.POST})
	public String adminAddUser(Model model, User user, @RequestParam List<String> roleNames, BindingResult br){
		if (br.hasErrors()) {
			model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
			return "forward:/userList";
		}
		User existingUser = userDAO.findByUserName(user.getUserName());

		if (existingUser != null) {
			model.addAttribute("errMsg", "Username is already taken.");
			return "forward:/userList";
		}
		List<Role> roles = new ArrayList<Role>();

		for (String name : roleNames) {
			Role foundRole = roleDAO.findByName(name);

			if (foundRole == null) {
				foundRole = new Role();
				foundRole.setName(name);
				if (!roleDAO.create(foundRole)) {
					model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
					return "forward:/userList";
				}
			}
			roles.add(roleDAO.findByName(name));
		}
		user.setRoles(roles);

		if (!userDAO.create(user)) {
			model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
			return "forward:/userList";
		}
		model.addAttribute("successMsg", "Successfully registered new user!");
		return "forward:/userList";
	}

	@RequestMapping(value="/adminDeleteUser", method = {RequestMethod.GET, RequestMethod.POST})
	public String adminDeleteUser(Model model, @RequestParam Integer id){
		User targetUser = userDAO.read(id);
		if (!userDAO.delete(targetUser)) {
			model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
			return "forward:/userList";
		}
		
		model.addAttribute("successMsg", "User " + targetUser.getUserName()+", " + targetUser.getFirstName() + " " + targetUser.getLastName() + " has been deleted.");
		return "forward:/userList";
	}

	@RequestMapping(value="/adminEditUser", method = {RequestMethod.GET, RequestMethod.POST})
	public String adminEditUser(Model model, User userDetails, @RequestParam List<String> roleNames, BindingResult br){
		User dbUser= userDAO.read(userDetails.getId());
		if (dbUser == null) {
			model.addAttribute("errMsg", "This user could not be found. Please try again.");
			return "forward:/userList";
		}
		
		
		dbUser.setFirstName(userDetails.getFirstName());
		dbUser.setLastName(userDetails.getLastName());
		dbUser.setUserName(userDetails.getUserName());
		
		List<Role> roles = new ArrayList<Role>();
		for (String name : roleNames) {
			Role foundRole = roleDAO.findByName(name);

			if (foundRole == null) {
				foundRole = new Role();
				foundRole.setName(name);
				if (!roleDAO.create(foundRole)) {
					model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
					return "forward:/userList";
				}
			}
			roles.add(roleDAO.findByName(name));
		}
		dbUser.setRoles(roles);
		
		dbUser = userDAO.update(dbUser);
		
		if(dbUser == null){
			model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
			return "forward:/userList";
		}
		model.addAttribute("successMsg", "Successfully updated user!");
		return "forward:/userList";
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
