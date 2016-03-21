package com.fdmgroup.tradingplatform.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tp_role")
@NamedQueries({
	@NamedQuery(name = "Role.read", query = "SELECT r FROM Role r WHERE r.id = :id"),
	@NamedQuery(name = "Role.findByUser", query = "SELECT r FROM Role r WHERE :user MEMBER OF r.users"),
	@NamedQuery(name = "Role.findByName", query = "SELECT r FROM Role r WHERE r.name = :name"),
	@NamedQuery(name = "Role.readAll", query = "SELECT r FROM Role r")
})
public class Role implements IStorable, Serializable{
	private static final long serialVersionUID = 2604339249740602808L;

	@Column(name="ROLE_ID")
	@Id
	@GeneratedValue(generator="RoleSeq", strategy=GenerationType.SEQUENCE) 
    @SequenceGenerator(name="RoleSeq",sequenceName="ROLE_ID_SEQ", allocationSize=1, initialValue=5)
	private Integer id;

	@Column(length = 25, nullable = true, name="ROLE_NAME")
	private String name;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy="roles")
	private List<User> users;
	
	public Role(Integer id, String name) {
		this.name = name;
		this.id = id;
	}
	public Role() {}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Role other = (Role) obj;
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
		return true;
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + "]";
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
}
