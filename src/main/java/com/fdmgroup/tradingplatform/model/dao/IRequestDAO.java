package com.fdmgroup.tradingplatform.model.dao;


import java.util.List;

import com.fdmgroup.tradingplatform.model.entity.Request;
import com.fdmgroup.tradingplatform.model.entity.User;

/**
 * Additional methods for Data Access Objects dealing with Requests
 */
public interface IRequestDAO extends IStorage<Request> {
	/**
	 * Find the requests for the provided user.
	 * @param selectedUser
	 * @return A list of requests for the provided user or null on failure.
	 */
	List<Request> findRequestsByUser(User selectedUser);

	/**
	 * Find the requests for the provided user with the 'ACTIVE' status.
	 * @param selectedUser
	 * @return A list of active requests for the provided user or null on failure.
	 */
	List<Request> findActiveRequestsByUser(User selectedUser);
}
