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

/**
 * Request mappings for administrator actions 
 */
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
			//This 'errMsg' attribute is displayed in a modal when the page loads
			model.addAttribute("errMsg", "An error while searching for users. Please try again. If the problem persists, contact technical support.");
			return "userList";
		}
		model.addAttribute("users", users);
		//the user bean is a prototype bean, so this will be a new user
		//that will be populated by the spring form if the admin edits or adds a user.
		model.addAttribute("user", (User) context.getBean("user"));
		return "userList";
	}

	@RequestMapping(value="/adminAddUser", method = {RequestMethod.GET, RequestMethod.POST})
	public String adminAddUser(Model model, User user, @RequestParam List<String> roleNames, BindingResult br){
		if (br.hasErrors()) {
			model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
			return "forward:/userList";
		}

		//usernames should be unique
		User existingUser = userDAO.findByUserName(user.getUserName());
		if (existingUser != null) {
			model.addAttribute("errMsg", "Username is already taken.");
			return "forward:/userList";
		}
		
		/*For every role name specified add it to the user's list of roles.
		 * If the role does not yet exist in the database, create it*/
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
		else{
			model.addAttribute("successMsg", "Successfully registered new user!");
			return "forward:/userList";
		}
	}

	@RequestMapping(value="/adminDeleteUser", method = {RequestMethod.GET, RequestMethod.POST})
	public String adminDeleteUser(Model model, @RequestParam Integer id){
		User targetUser = userDAO.read(id);
		if (!userDAO.delete(targetUser)) {
			model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
			return "forward:/userList";
		}
		else{
			model.addAttribute("successMsg", "User " + targetUser.getUserName()+", " + targetUser.getFirstName() + " " + targetUser.getLastName() + " has been deleted.");
			return "forward:/userList";
		}
	}

	@RequestMapping(value="/adminEditUser", method = {RequestMethod.GET, RequestMethod.POST})
	public String adminEditUser(Model model, User userAttribute, @RequestParam List<String> roleNames, BindingResult br){
		User persistentUser = userDAO.read(userAttribute.getId());
		if (persistentUser == null) {
			model.addAttribute("errMsg", "This user could not be found. Please try again.");
			return "forward:/userList";
		}
		
		//Need to set the fields of the actual, persistent user from the database and call merge on that,
		//as opposed to the one from the spring form, which contains only the relevant, updated info
		persistentUser.setFirstName(userAttribute.getFirstName());
		persistentUser.setLastName(userAttribute.getLastName());
		persistentUser.setUserName(userAttribute.getUserName());
		
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
		persistentUser.setRoles(roles);
		
		persistentUser = userDAO.update(persistentUser);
		if(persistentUser == null){
			model.addAttribute("errMsg", "An error occurred while processing your request. Please try again.");
			return "forward:/userList";
		}
		else{
			model.addAttribute("successMsg", "Successfully updated user!");
			return "forward:/userList";
		}
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
