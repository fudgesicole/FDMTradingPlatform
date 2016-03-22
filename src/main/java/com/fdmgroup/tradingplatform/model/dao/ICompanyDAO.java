package com.fdmgroup.tradingplatform.model.dao;


import com.fdmgroup.tradingplatform.model.entity.Company;

public interface ICompanyDAO extends IStorage<Company> {
	Company findByStockId(int stockId);
	Company findByName(String name);
}
