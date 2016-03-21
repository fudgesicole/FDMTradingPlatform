package com.fdmgroup.tradingplatform.model.dao;

import java.util.List;

import com.fdmgroup.tradingplatform.model.entity.Share;
import com.fdmgroup.tradingplatform.model.entity.User;
import com.fdmgroup.tradingplatform.util.DBUtil;

public class DBUserDAO implements IUserDAO {

	private DBUtil dbutil;
	
	@Override
	public List<User> readAll() {
		return dbutil.findListByFields("user.readAll");
	}

	@Override
	public boolean create(User newUser) {
		return dbutil.createHelper(newUser);
	}

	@Override
	public User read(int id) {
		return dbutil.findUniqueByFields("user.read", "id", id);
	}

	@Override
	public User update(User updatedUser) {
		return dbutil.updateHelper(updatedUser);
	}

	@Override
	public boolean delete(User targetUser) {
		targetUser.getRoles().clear();
		//Need to remove all of the roles from the user
		//before deleting for integrity. Also update targetUser with the
		//new persistent user returned by hibernate
		if((targetUser = dbutil.updateHelper(targetUser)) == null)
			return false;
		return dbutil.deleteHelper(targetUser);
	}

	@Override
	public boolean deleteById(int id) {
		User targetUser = read(id);
		if(targetUser == null){
			return false;
		}
		return delete(targetUser);
	}

	@Override
	public List<Share> getShareholderShares(User user) {
		return dbutil.findListByFields("share.readByShareholderId", "id", user.getId());
	}

	@Override
	public User findByUserName(String userName) {
		return dbutil.findUniqueByFields("user.readByUserName", "userName", userName);
	}

	public DBUtil getDbutil() {
		return dbutil;
	}

	public void setDbutil(DBUtil dbutil) {
		this.dbutil = dbutil;
	}

}
