package com.fdmgroup.tradingplatform.model.dao;

import java.util.List;

import com.fdmgroup.tradingplatform.model.entity.Trade;
import com.fdmgroup.tradingplatform.model.entity.User;

/**
 * Additional methods for Data Access Objects dealing with Trades
 */
public interface ITradeDAO extends IStorage<Trade>{
	/**
	 * Find the trades belonging to the provided user.
	 * @param user
	 * @return The list of trades belonging to the user, or null on failure.
	 */
	List<Trade> findTradesByUser(User user);
}
