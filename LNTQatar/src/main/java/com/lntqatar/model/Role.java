package com.lntqatar.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.lntqatar.model.Privilege;

@Entity
@Table(name = "role_tbl")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_roleId")
	private int userRoleId;

	@Column(name = "user_roleName")
	private String userRoleName;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "role_privilege", joinColumns = { @JoinColumn(name = "user_roleId") }, inverseJoinColumns = { @JoinColumn(name = "privelege_Id") })
	private Set<Privilege> privileges = new HashSet<Privilege>();

	/**
	 * Constructor method
	 */
	public Role() {

	}

	/**
	 * Constructor method which accepts user role Name
	 */
	public Role(String userRoleName) {
		this.userRoleName = userRoleName;

	}

	/**
	 * Retrieves user role id
	 * 
	 * @return id
	 */
	public int getUserRoleId() {
		return userRoleId;
	}

	/**
	 * Sets user Role Id
	 * 
	 * @param userRoleId
	 */
	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}

	/**
	 * Retrieves user role name
	 * 
	 * @return user role name
	 */
	public String getUserRoleName() {
		return userRoleName;
	}

	/**
	 * Sets user role name
	 * 
	 * @param userRoleName
	 */
	public void setUserRoleName(String userRoleName) {
		this.userRoleName = userRoleName;
	}

	/**
	 * Retrieve list of privilege
	 * 
	 * @return Privilege privilege
	 */
	public Set<Privilege> getPrivileges() {
		return privileges;
	}

	/**
	 * Sets privilege
	 * 
	 * @param privileges
	 */
	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}

}
