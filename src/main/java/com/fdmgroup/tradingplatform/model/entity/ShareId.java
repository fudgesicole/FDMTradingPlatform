package com.fdmgroup.tradingplatform.model.entity;

import java.io.Serializable;

public class ShareId implements Serializable{
	private static final long serialVersionUID = 5308641038812216357L;
	
	private Integer company;
	private Integer shareHolder;
	private int shareCount;
	
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
		ShareId other = (ShareId) obj;
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
		return "ShareId [company=" + company + ", shareHolder=" + shareHolder
				+ ", shareCount=" + shareCount + "]";
	}
	public Integer getCompany() {
		return company;
	}
	public void setCompany(Integer company) {
		this.company = company;
	}
	public Integer getShareHolder() {
		return shareHolder;
	}
	public void setShareHolder(Integer shareHolder) {
		this.shareHolder = shareHolder;
	}
	public int getShareCount() {
		return shareCount;
	}
	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}
	
}
