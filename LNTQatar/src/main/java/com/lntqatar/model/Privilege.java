package com.lntqatar.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.lntqatar.model.Role;

@Entity
@Table(name = "privilege_tbl")
public class Privilege {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "privilege_id")
	private int Id;
	@Column(name = "privilege_rights")
	private String privilege;
	@ManyToMany(mappedBy = "privileges")
	private Set<Role> role = new HashSet<Role>();

	/**
	 * Constructor method
	 */
	public Privilege() {
	}

	/**
	 * Constructor method
	 * 
	 * @param id
	 * @param priviledgeRights
	 * 
	 */
	public Privilege(String privilege) {
		this.privilege = privilege;

	}

	/**
	 * Retrieves id
	 * 
	 * @return id
	 */
	public int getId() {
		return Id;
	}

	/**
	 * Sets Id
	 * 
	 * @param id
	 */
	public void setId(int id) {
		Id = id;
	}

	/**
	 * Retrieves privilege
	 * 
	 * @return privilege
	 */
	public String getPrivilege() {
		return privilege;
	}

	/**
	 * Sets privilege
	 * 
	 * @param privilege
	 */
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	/**
	 * Retrieves Role Object
	 * 
	 * @return
	 */
	public Set<Role> getRole() {
		return role;
	}

	/**
	 * Sets Role Object
	 * 
	 * @param role
	 */
	public void setRole(Set<Role> role) {
		this.role = role;
	}
}
