package com.fdmgroup.tradingplatform.model.dao;

import java.math.BigDecimal;
import java.util.List;

import com.fdmgroup.tradingplatform.model.entity.AuthorizedShare;
import com.fdmgroup.tradingplatform.model.entity.Company;
import com.fdmgroup.tradingplatform.util.DBUtil;

public class DBCompanyDAO implements ICompanyDAO {

	private DBUtil dbutil;
	
	@Override
	public List<Company> readAll() {
		return dbutil.findListByFields("company.readAll");
	}

	@Override
	public boolean create(Company newCompany) {
		List<AuthorizedShare> shares = newCompany.getAuthorizedShares();
		//Hibernate cannot generate values for non id columns using sequences, so I do this manually.
		BigDecimal nextId = dbutil.runNativeQuerySingleResult("select MAX(COMPANY_ID) + 1 from TP_COMPANY");
		newCompany.setId(nextId.intValue());
		newCompany.setStockId(nextId.intValue());
		//I cannot add an authorized share with a null company
		//and I cannot create a new company that references authorized shares
		//that don't yet exist in the database. Therefore I create the company,
		//then create shares and add the newly created persistent shares.
		if(!dbutil.createHelper(newCompany))
			return false;
		for(AuthorizedShare share : shares){
			share.setCompany(newCompany);
			if(!dbutil.createHelper(share))
				return false;
		}
		return dbutil.createHelper(newCompany);
	}

	@Override
	public Company read(int id) {
		return dbutil.findUniqueByFields("company.read", "id", id);
	}

	@Override
	public Company update(Company updatedCompany) {
		return dbutil.updateHelper(updatedCompany);
	}

	@Override
	public boolean delete(Company targetCompany) {
		return dbutil.deleteHelper(targetCompany);
	}

	@Override
	public boolean deleteById(int id) {
		Company targetCompany = read(id);
		if(targetCompany == null){
			//TODO: log attempt to delete non-existent company.
			return false;
		}
		return dbutil.deleteHelper(targetCompany);
	}

	@Override
	public Company findByStockId(int stockId) {
		return dbutil.findUniqueByFields("company.readByStockId", "stockId", stockId);
	}

	@Override
	public Company findByName(String name) {
		return dbutil.findUniqueByFields("company.readByName", "name", name);
	}

	public DBUtil getDbutil() {
		return dbutil;
	}

	public void setDbutil(DBUtil dbutil) {
		this.dbutil = dbutil;
	}

}
