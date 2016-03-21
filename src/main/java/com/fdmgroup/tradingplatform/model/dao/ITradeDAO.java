package com.fdmgroup.tradingplatform.model.dao;

import java.util.List;

import com.fdmgroup.tradingplatform.model.entity.Trade;
import com.fdmgroup.tradingplatform.model.entity.User;

public interface ITradeDAO extends IStorage<Trade>{
	List<Trade> findTradesByUser(User user);
}
