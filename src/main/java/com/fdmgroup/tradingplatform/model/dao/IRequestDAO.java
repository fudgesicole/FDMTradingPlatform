package com.fdmgroup.tradingplatform.model.dao;


import java.util.List;

import com.fdmgroup.tradingplatform.model.entity.Request;
import com.fdmgroup.tradingplatform.model.entity.User;

public interface IRequestDAO extends IStorage<Request> {
	List<Request> findRequestsByUser(User selectedUser);
	List<Request> findActiveRequestsByUser(User selectedUser);
}
