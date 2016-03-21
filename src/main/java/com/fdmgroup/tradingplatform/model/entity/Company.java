package com.fdmgroup.tradingplatform.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="tp_company")
@NamedQueries({
	@NamedQuery(name = "company.readByStockId", query = "SELECT c FROM Company c WHERE c.id = :stockId"),
	@NamedQuery(name = "company.readByName", query = "SELECT c FROM Company c WHERE c.name = :name"),
	@NamedQuery(name = "company.readAll", query = "SELECT c FROM Company c"),
	@NamedQuery(name = "company.read", query = "SELECT c FROM Company c WHERE c.id = :id")
})
@SequenceGenerator(name="CompanySeq",sequenceName="COMPANY_ID_SEQ", allocationSize=1, initialValue=864) 
public class Company implements IStorable, Serializable{
	private static final long serialVersionUID = -4250483935471638891L;

	@Column(name = "COMPANY_ID")
	@Id 
	private Integer id;
	
	@Column(name = "STOCK_ID")
	private Integer stockId;
	
	@Column(length = 50, nullable = false, name="COMPANY_NAME")
	private String name;
	
	@Column(nullable = false, name = "STARTING_PRICE")
	private Double startingPrice;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "company")
	private List<Request> requests;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "company")
	private List<Trade> trades;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "company")
	private List<Share> shares;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "company")
	private List<AuthorizedShare> authorizedShares;
	
	public List<Share> getShares() {
		return shares;
	}
	public void setShares(List<Share> shares) {
		this.shares = shares;
	}
	public List<AuthorizedShare> getAuthorizedShares() {
		return authorizedShares;
	}
	public void setAuthorizedShares(List<AuthorizedShare> authorizedShares) {
		this.authorizedShares = authorizedShares;
	}
	public Integer getId() {
		return id;
	}
	public List<Request> getRequests() {
		return requests;
	}
	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Double getStartingPrice() {
		return startingPrice;
	}
	public void setStartingPrice(Double startingPrice) {
		this.startingPrice = startingPrice;
	}
	
	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", startingPrice="
				+ startingPrice + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((startingPrice == null) ? 0 : startingPrice.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (startingPrice == null) {
			if (other.startingPrice != null)
				return false;
		} else if (!startingPrice.equals(other.startingPrice))
			return false;
		return true;
	}
	public List<Trade> getTrades() {
		return trades;
	}
	public void setTrades(List<Trade> trades) {
		this.trades = trades;
	}
	public Integer getStockId() {
		return stockId;
	}
	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}
	
}
