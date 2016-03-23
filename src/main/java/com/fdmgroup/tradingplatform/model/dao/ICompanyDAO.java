package com.fdmgroup.tradingplatform.model.dao;


import com.fdmgroup.tradingplatform.model.entity.Company;
/**
 * Additional methods for Data Access Objects dealing with Companies
 */
public interface ICompanyDAO extends IStorage<Company> {
	/**
	 * Find a company by its stock id
	 * @param stockId
	 * @return the company with the given stock id, or null if none found.
	 */
	Company findByStockId(int stockId);
	
	/**
	 * Find a company by its name
	 * @param name
	 * @return the company with the given name, or null if none found.
	 */
	Company findByName(String name);
}
