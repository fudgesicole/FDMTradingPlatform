package com.fdmgroup.tradingplatform.model.dao;


import java.util.List;

import com.fdmgroup.tradingplatform.model.entity.Share;
import com.fdmgroup.tradingplatform.model.entity.User;


/**
 * Additional methods for Data Access Objects dealing with users
 */
public interface IUserDAO extends IStorage<User>{
	
	/**
	 * Find all of the shares for the provided user
	 * @param user
	 * @return The list of shares for the given user, or null if storage could not be read.
	 */
	List<Share> getShareholderShares(User user);

	/**
	 * Find a user based on their unique username
	 * @param userName
	 * @return the user with the provided username, or null if the user could not be found.
	 */
	User findByUserName(String userName);

}
