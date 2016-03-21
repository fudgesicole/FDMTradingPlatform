package com.fdmgroup.tradingplatform.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;


@Entity @IdClass(ShareId.class)
@Table(name="TP_CURRENT_SHAREHOLDER_SHARES")
@NamedQuery(name = "share.readByShareholderId", query = "SELECT s FROM Share s JOIN s.shareHolder sh WHERE sh.id = :id")
@Immutable
public class Share implements IStorable, Serializable{
	private static final long serialVersionUID = -1439968627462002892L;

	@Id
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name="STOCK_ID", referencedColumnName="STOCK_ID")
	private Company company;
	
	@Id
	@Column(nullable = true, name = "SHARES")
	private int shareCount;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name="SHAREHOLDER_ID", referencedColumnName = "USER_ID")
	private User shareHolder;
	
	public User getShareHolder() {
		return shareHolder;
	}
	public void setShareHolder(User shareHolder) {
		this.shareHolder = shareHolder;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public int getShareCount() {
		return shareCount;
	}
	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + shareCount;
		result = prime * result
				+ ((shareHolder == null) ? 0 : shareHolder.hashCode());
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
		Share other = (Share) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (shareCount != other.shareCount)
			return false;
		if (shareHolder == null) {
			if (other.shareHolder != null)
				return false;
		} else if (!shareHolder.equals(other.shareHolder))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Share [company=" + company + ", shareCount=" + shareCount
				+ ", shareHolder=" + shareHolder + "]";
	}

}
