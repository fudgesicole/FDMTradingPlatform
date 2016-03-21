package com.fdmgroup.tradingplatform.model.dao;

import java.util.List;

import com.fdmgroup.tradingplatform.model.entity.Role;
import com.fdmgroup.tradingplatform.model.entity.User;

public interface IRoleDAO extends IStorage<Role> {
	List<Role> findRolesByUser(User user);
	Role findByName(String name);
}
