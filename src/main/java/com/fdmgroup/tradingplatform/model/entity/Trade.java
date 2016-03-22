package com.fdmgroup.tradingplatform.model.entity;

import java.io.Serializable;
import java.sql.Date;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="tp_trade")
@NamedQueries({
	@NamedQuery(name = "trade.read", query = "SELECT t FROM Trade t WHERE t.id = :id"),
	@NamedQuery(name = "Trade.findByUser", query = "SELECT t FROM Trade t WHERE t.buyer = :user OR t.seller = :user"),
	@NamedQuery(name = "trade.readAll", query = "SELECT t FROM Trade t")
})
public class Trade implements IStorable, Serializable{
	private static final long serialVersionUID = -4541861134324429001L;

	@Column(name = "TRADE_ID")
	@Id
	@GeneratedValue(generator="TradeSeq", strategy=GenerationType.SEQUENCE) 
    @SequenceGenerator(name="TradeSeq",sequenceName="TRADE_ID_SEQ", allocationSize=1, initialValue=1) 
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="STOCK_ID", referencedColumnName="STOCK_ID")
	private Company company;
	
	@Column(nullable = false, name="TRANSACTION_TIME")
	private Date transactionTime;
	
	@Column(nullable = false, name="SHARES")
	private Integer shares;
	
	@Column(nullable = true, name="SHARE_PRICE")
	private Double sharePrice;

	@Column(nullable = true, name = "PRICE_TOTAL")
	private Double priceTotal;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="BUYER_ID",referencedColumnName = "USER_ID")
	private User buyer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="SELLER_ID", referencedColumnName = "USER_ID")
	private User seller;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="BUY_REQUEST_ID", referencedColumnName = "REQUEST_ID")
	private Request buyRequest;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="SELL_REQUEST_ID", referencedColumnName = "REQUEST_ID")
	private Request sellRequest;
	
	public Double getSharePrice() {
		return sharePrice;
	}
	
	public void setSharePrice(Double sharePrice) {
		this.sharePrice = sharePrice;
	}
	
	public Trade() {
		super();
	}
	
	@Override
	public String toString() {
		return "Trade [id=" + id + ", company=" + company
				+ ", transactionTime=" + transactionTime + ", shares=" + shares
				+ ", sharePrice=" + sharePrice + ", priceTotal=" + priceTotal
				+ ", buyer=" + buyer + ", seller=" + seller + ", buyRequest="
				+ buyRequest + ", sellRequest=" + sellRequest + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((buyRequest == null) ? 0 : buyRequest.hashCode());
		result = prime * result + ((buyer == null) ? 0 : buyer.hashCode());
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((priceTotal == null) ? 0 : priceTotal.hashCode());
		result = prime * result
				+ ((sellRequest == null) ? 0 : sellRequest.hashCode());
		result = prime * result + ((seller == null) ? 0 : seller.hashCode());
		result = prime * result
				+ ((sharePrice == null) ? 0 : sharePrice.hashCode());
		result = prime * result + ((shares == null) ? 0 : shares.hashCode());
		result = prime * result
				+ ((transactionTime == null) ? 0 : transactionTime.hashCode());
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
		Trade other = (Trade) obj;
		if (buyRequest == null) {
			if (other.buyRequest != null)
				return false;
		} else if (!buyRequest.equals(other.buyRequest))
			return false;
		if (buyer == null) {
			if (other.buyer != null)
				return false;
		} else if (!buyer.equals(other.buyer))
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
		if (priceTotal == null) {
			if (other.priceTotal != null)
				return false;
		} else if (!priceTotal.equals(other.priceTotal))
			return false;
		if (sellRequest == null) {
			if (other.sellRequest != null)
				return false;
		} else if (!sellRequest.equals(other.sellRequest))
			return false;
		if (seller == null) {
			if (other.seller != null)
				return false;
		} else if (!seller.equals(other.seller))
			return false;
		if (sharePrice == null) {
			if (other.sharePrice != null)
				return false;
		} else if (!sharePrice.equals(other.sharePrice))
			return false;
		if (shares == null) {
			if (other.shares != null)
				return false;
		} else if (!shares.equals(other.shares))
			return false;
		if (transactionTime == null) {
			if (other.transactionTime != null)
				return false;
		} else if (!transactionTime.equals(other.transactionTime))
			return false;
		return true;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public Date getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}
	public Integer getShares() {
		return shares;
	}
	public void setShares(Integer shares) {
		this.shares = shares;
	}
	public Double getPriceTotal() {
		return priceTotal;
	}
	public void setPriceTotal(Double priceTotal) {
		this.priceTotal = priceTotal;
	}
	public User getBuyer() {
		return buyer;
	}
	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}
	public User getSeller() {
		return seller;
	}
	public void setSeller(User seller) {
		this.seller = seller;
	}
	public Request getBuyRequest() {
		return buyRequest;
	}
	public void setBuyRequest(Request buyRequest) {
		this.buyRequest = buyRequest;
	}
	public Request getSellRequest() {
		return sellRequest;
	}
	public void setSellRequest(Request sellRequest) {
		this.sellRequest = sellRequest;
	}
}
