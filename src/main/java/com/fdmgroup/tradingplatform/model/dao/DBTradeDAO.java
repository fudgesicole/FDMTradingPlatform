package com.fdmgroup.tradingplatform.model.dao;

import java.util.List;

import com.fdmgroup.tradingplatform.model.entity.Trade;
import com.fdmgroup.tradingplatform.model.entity.User;
import com.fdmgroup.tradingplatform.util.DBUtil;
/**
 * An implementation of ITradeDAO for accessing trade objects
 * stored in a database.
 */
public class DBTradeDAO implements ITradeDAO {

	private DBUtil dbutil;
	
	@Override
	public List<Trade> readAll() {
		return dbutil.findListByFields("Trade.readAll");
	}

	@Override
	public boolean create(Trade newTrade) {
		return dbutil.createHelper(newTrade);
	}

	@Override
	public Trade read(int id) {
		return dbutil.findUniqueByFields("Trade.read", "id", id);
	}

	@Override
	public Trade update(Trade updatedTrade) {
		return dbutil.updateHelper(updatedTrade);
	}

	@Override
	public boolean delete(Trade targetedTrade) {
		return dbutil.deleteHelper(targetedTrade);
	}

	@Override
	public boolean deleteById(int id) {
		//need the actual persistent object to delete
		Trade targetTrade = read(id);
		//If the target trade could not be found in the database,
		//return false to signify failure.
		if(targetTrade == null)
			return false;
		return dbutil.deleteHelper(read(id));
	}

	@Override
	public List<Trade> findTradesByUser(User user) {
		return dbutil.findListByFields("Trade.findByUser", "user", user);
	}

	public DBUtil getDbutil() {
		return dbutil;
	}

	public void setDbutil(DBUtil dbutil) {
		this.dbutil = dbutil;
	}
}
