package com.fdmgroup.tradingplatform.model.dao;

import java.util.List;

import com.fdmgroup.tradingplatform.model.entity.Role;
import com.fdmgroup.tradingplatform.model.entity.User;
import com.fdmgroup.tradingplatform.util.DBUtil;

public class DBRoleDAO implements IRoleDAO {

	private DBUtil dbutil;
	
	@Override
	public List<Role> readAll() {
		return dbutil.findListByFields("Role.readAll");
	}

	@Override
	public boolean create(Role newRole) {
		return dbutil.createHelper(newRole);
	}

	@Override
	public Role read(int id) {
		return dbutil.findUniqueByFields("Role.read", "id", id);
	}

	@Override
	public Role update(Role updatedRole) {
		return dbutil.updateHelper(updatedRole);
	}

	@Override
	public boolean delete(Role targetRole) {
		return dbutil.deleteHelper(targetRole);
	}

	@Override
	public boolean deleteById(int id) {
		Role targetRole = read(id);
		if(targetRole == null)
			return false;
		return dbutil.deleteHelper(targetRole);
	}

	@Override
	public List<Role> findRolesByUser(User user) {
		return dbutil.findListByFields("Role.findByUser", "user", user);
	}

	public DBUtil getDbutil() {
		return dbutil;
	}

	public void setDbutil(DBUtil dbutil) {
		this.dbutil = dbutil;
	}

	@Override
	public Role findByName(String name) {
		return dbutil.findUniqueByFields("Role.findByName", "name", name);
	}

}