
package com.fdmgroup.tradingplatform.model.entity;


import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="tp_request")
@NamedQueries({
	@NamedQuery(name = "Request.readAll", query = "SELECT r FROM Request r"),
	@NamedQuery(name = "Request.findByUser", query = "SELECT r FROM Request r WHERE r.shareholder = :user"),
	@NamedQuery(name = "Request.read", query = "SELECT r FROM Request r WHERE r.id = :id")
})
@NamedStoredProcedureQuery(name = "processRequests", procedureName = "TP_REQUEST_PKG.ProcessRequests")
public class Request implements IStorable, Serializable{
	private static final long serialVersionUID = 6337630055939277409L;

	@Column(nullable = false, name = "REQUEST_ID")
	@Id
	@GeneratedValue(generator="RequestSeq", strategy=GenerationType.SEQUENCE)
    @SequenceGenerator(name="RequestSeq",sequenceName="REQUEST_ID_SEQ", allocationSize=1, initialValue=1800)
	private Integer id;
	
	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name="PARENT_REQUEST_ID", referencedColumnName = "REQUEST_ID")
	private Request parent;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true, mappedBy="parent")
	private Request child;
	
	
	@Column(nullable = true, name = "SHARES_FILLED")
	private Integer sharesFilled;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SHAREHOLDER_ID", referencedColumnName = "USER_ID")
	private User shareholder;
	
	
	@Column(nullable = false, name = "REQUEST_DATE")
	private Date requestDate;
	
	@Column(length = 4, nullable = false, name = "BUY_SELL")
	private String type;
	
	@Column(length = 20, nullable = false, name = "STATUS")
	private String status;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="STOCK_ID", referencedColumnName="STOCK_ID")
	private Company company;
	
	@Column(nullable = false, name = "SHARES")
	private Integer shareCount;
	
	@Column(nullable = false, name = "MINIMUM_SHARES")
	private Integer minShares;
	
	@Column(length = 20, nullable = false, name = "TIME_IN_FORCE")
	private String timeInForce;
	
	
	@Column(nullable = true, name = "LIMIT_PRICE")
	private Double limitPrice;
	
	@Column(nullable = true, name = "STOP_PRICE")
	private Double stopPrice;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="buyRequest")
	private Trade buyTrade;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="sellRequest")
	private Trade sellTrade;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Request getParent() {
		return parent;
	}

	public void setParent(Request parent) {
		this.parent = parent;
	}

	public Integer getSharesFilled() {
		return sharesFilled;
	}

	public void setSharesFilled(Integer sharesFilled) {
		this.sharesFilled = sharesFilled;
	}

	public User getShareholder() {
		return shareholder;
	}

	public void setShareholder(User shareholder) {
		this.shareholder = shareholder;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Integer getMinShares() {
		return minShares;
	}

	public void setMinShares(Integer minShares) {
		this.minShares = minShares;
	}

	public Double getLimitPrice() {
		return limitPrice;
	}

	public void setLimitPrice(Double limitPrice) {
		this.limitPrice = limitPrice;
	}

	public Double getStopPrice() {
		return stopPrice;
	}

	public void setStopPrice(Double stopPrice) {
		this.stopPrice = stopPrice;
	}
	
	@Override
	public String toString() {
		return "Request [id=" + id + ", parent=" + parent + ", sharesFilled="
				+ sharesFilled + ", shareholder=" + shareholder
				+ ", requestDate=" + requestDate + ", type=" + type
				+ ", status=" + status + ", company=" + company + ", shares="
				+ shareCount + ", minShares=" + minShares + ", timeInForce="
				+ timeInForce + ", limitPrice=" + limitPrice + ", stopPrice="
				+ stopPrice + "]";
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((buyTrade == null) ? 0 : buyTrade.hashCode());
		result = prime * result + ((child == null) ? 0 : child.hashCode());
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((limitPrice == null) ? 0 : limitPrice.hashCode());
		result = prime * result
				+ ((minShares == null) ? 0 : minShares.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result
				+ ((requestDate == null) ? 0 : requestDate.hashCode());
		result = prime * result
				+ ((sellTrade == null) ? 0 : sellTrade.hashCode());
		result = prime * result
				+ ((shareholder == null) ? 0 : shareholder.hashCode());
		result = prime * result + ((shareCount == null) ? 0 : shareCount.hashCode());
		result = prime * result
				+ ((sharesFilled == null) ? 0 : sharesFilled.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((stopPrice == null) ? 0 : stopPrice.hashCode());
		result = prime * result
				+ ((timeInForce == null) ? 0 : timeInForce.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Request other = (Request) obj;
		if (buyTrade == null) {
			if (other.buyTrade != null)
				return false;
		} else if (!buyTrade.equals(other.buyTrade))
			return false;
		if (child == null) {
			if (other.child != null)
				return false;
		} else if (!child.equals(other.child))
			return false;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (limitPrice == null) {
			if (other.limitPrice != null)
				return false;
		} else if (!limitPrice.equals(other.limitPrice))
			return false;
		if (minShares == null) {
			if (other.minShares != null)
				return false;
		} else if (!minShares.equals(other.minShares))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (requestDate == null) {
			if (other.requestDate != null)
				return false;
		} else if (!requestDate.equals(other.requestDate))
			return false;
		if (sellTrade == null) {
			if (other.sellTrade != null)
				return false;
		} else if (!sellTrade.equals(other.sellTrade))
			return false;
		if (shareholder == null) {
			if (other.shareholder != null)
				return false;
		} else if (!shareholder.equals(other.shareholder))
			return false;
		if (shareCount == null) {
			if (other.shareCount != null)
				return false;
		} else if (!shareCount.equals(other.shareCount))
			return false;
		if (sharesFilled == null) {
			if (other.sharesFilled != null)
				return false;
		} else if (!sharesFilled.equals(other.sharesFilled))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (stopPrice == null) {
			if (other.stopPrice != null)
				return false;
		} else if (!stopPrice.equals(other.stopPrice))
			return false;
		if (timeInForce == null) {
			if (other.timeInForce != null)
				return false;
		} else if (!timeInForce.equals(other.timeInForce))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTimeInForce() {
		return timeInForce;
	}

	public void setTimeInForce(String timeInForce) {
		this.timeInForce = timeInForce;
	}

	public Request getChild() {
		return child;
	}

	public void setChild(Request child) {
		this.child = child;
	}

	public Trade getBuyTrade() {
		return buyTrade;
	}

	public void setBuyTrade(Trade buyTrade) {
		this.buyTrade = buyTrade;
	}

	public Trade getSellTrade() {
		return sellTrade;
	}

	public void setSellTrade(Trade sellTrade) {
		this.sellTrade = sellTrade;
	}

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}

}
