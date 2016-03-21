package com.fdmgroup.tradingplatform.model.dao;


import java.util.List;

import com.fdmgroup.tradingplatform.model.entity.Share;
import com.fdmgroup.tradingplatform.model.entity.User;

public interface IUserDAO extends IStorage<User>{
	List<Share> getShareholderShares(User user);

	User findByUserName(String userName);

}
