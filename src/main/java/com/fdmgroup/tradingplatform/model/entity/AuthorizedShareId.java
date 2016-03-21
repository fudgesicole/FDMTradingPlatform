package com.fdmgroup.tradingplatform.model.entity;

import java.io.Serializable;
import java.sql.Date;

public class AuthorizedShareId implements Serializable{
	private static final long serialVersionUID = 3998518720587594607L;
	
	private Integer company;
	private Date timeStart;

	public Integer getCompany() {
		return company;
	}
	@Override
	public String toString() {
		return "AuthorizedShareId [timeStart=" + timeStart + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result
				+ ((timeStart == null) ? 0 : timeStart.hashCode());
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
		AuthorizedShareId other = (AuthorizedShareId) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (timeStart == null) {
			if (other.timeStart != null)
				return false;
		} else if (!timeStart.equals(other.timeStart))
			return false;
		return true;
	}
	public void setCompany(Integer company) {
		this.company = company;
	}
	public Date getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}
	
}
