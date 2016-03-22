package com.fdmgroup.tradingplatform.model.dao;

import java.util.List;


import com.fdmgroup.tradingplatform.model.entity.Request;
import com.fdmgroup.tradingplatform.model.entity.User;
import com.fdmgroup.tradingplatform.util.DBUtil;

public class DBRequestDAO implements IRequestDAO {
	
	private DBUtil dbutil;
	
	@Override
	public List<Request> readAll() {
		return dbutil.findListByFields("request.readAll");
	}

	@Override
	public boolean create(Request newRequest) {
		if(!dbutil.createHelper(newRequest))
			return false;
		return dbutil.runStoredProcedure("processRequests");
	}

	@Override
	public Request read(int id) {
		return dbutil.findUniqueByFields("Request.read", "id", id);
	}

	@Override
	public Request update(Request updatedRequest) {
		return dbutil.updateHelper(updatedRequest);
	}

	@Override
	public boolean delete(Request targetRequest) {
		return dbutil.deleteHelper(targetRequest);
	}

	@Override
	public boolean deleteById(int id) {
		Request targetRequest = read(id);
		if(targetRequest == null)
			return false;
		return dbutil.deleteHelper(targetRequest);
	}

	@Override
	public List<Request> findRequestsByUser(User selectedUser) {
		return dbutil.findListByFields("Request.findByUser", "user", selectedUser);
	}

	public DBUtil getDbutil() {
		return dbutil;
	}

	public void setDbutil(DBUtil dbutil) {
		this.dbutil = dbutil;
	}

	@Override
	public List<Request> findActiveRequestsByUser(User selectedUser) {
		return dbutil.findListByFields("Request.findActiveRequestsByUser", "shareholder", selectedUser);
	}

}
