package com.marcos.security.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
	
	public Role() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private long roleId;
	
	private String name;
	
	
	public enum Values {
		
		ADMIN(1),
		BASIC(2);
		
		long roleId;

		private Values(long roleId) {
			this.roleId = roleId;
		}
		
		
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
