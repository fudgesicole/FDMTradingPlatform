package com.fdmgroup.tradingplatform.model.dao;

import java.util.List;

import com.fdmgroup.tradingplatform.model.entity.Role;
import com.fdmgroup.tradingplatform.model.entity.User;

/**
 * Additional methods for Data Access Objects dealing with roles.
 */
public interface IRoleDAO extends IStorage<Role> {
	/**
	 * Find a user's list of roles
	 * @param user
	 * @return The list of roles the given user possesses.
	 */
	List<Role> findRolesByUser(User user);
	
	/**
	 * Find a role by name
	 * @param name
	 * @return The role with the given name, or null if none found.
	 */
	Role findByName(String name);
}
