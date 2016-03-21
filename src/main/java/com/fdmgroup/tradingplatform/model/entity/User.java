package com.fdmgroup.tradingplatform.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="tp_users")
@NamedQueries({
	@NamedQuery(name = "user.readByUserName", query = "SELECT u FROM User u WHERE u.userName = :userName"),
	@NamedQuery(name = "user.readAll", query = "SELECT u FROM User u"),
	@NamedQuery(name = "user.read", query = "SELECT u FROM User u WHERE u.id = :id")
})
public class User implements IStorable, Serializable{
	private static final long serialVersionUID = 3507532745087774284L;


	@Column(name = "USER_ID")
	@Id
	@GeneratedValue(generator="UserSeq", strategy=GenerationType.SEQUENCE) 
    @SequenceGenerator(name="UserSeq",sequenceName="USER_ID_SEQ", allocationSize=1, initialValue=1000864) 
	private Integer id;
	
	
	@Column(length = 50, nullable = false, name="USER_NAME")
	private String userName;

	@Column(length = 50, nullable = false, name="FIRST_NAME")
	private String firstName;
	
	@Column(length = 50, nullable = false, name="LAST_NAME")
	private String lastName;
	
	@Column(length = 100, nullable = false, name="PW")
	private String passWord;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(
			   name = "TP_USER_ROLE", 
			   joinColumns = @JoinColumn(name = "USER_ID"), 
			   inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	private List<Role> roles;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shareholder")
	List<Request> requests;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "buyer")
	List<Trade> buyTrades;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "seller")
	List<Trade> sellTrades;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "shareHolder")
	List<Share> shares;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id){
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", firstName="
				+ firstName + ", lastName=" + lastName + ", passWord="
				+ passWord + ", roles=" + roles + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((passWord == null) ? 0 : passWord.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
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
		User other = (User) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (passWord == null) {
			if (other.passWord != null)
				return false;
		} else if (!passWord.equals(other.passWord))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	public List<Request> getRequests() {
		return requests;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

	public List<Trade> getBuyTrades() {
		return buyTrades;
	}

	public void setBuyTrades(List<Trade> buyTrades) {
		this.buyTrades = buyTrades;
	}

	public List<Trade> getSellTrades() {
		return sellTrades;
	}

	public void setSellTrades(List<Trade> sellTrades) {
		this.sellTrades = sellTrades;
	}

	public List<Share> getShares() {
		return shares;
	}

	public void setShares(List<Share> shares) {
		this.shares = shares;
	}

}
