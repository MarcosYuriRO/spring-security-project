package com.marcos.security.service;

import org.springframework.stereotype.Service;

import com.marcos.security.entities.Role;
import com.marcos.security.repository.RoleRepository;

@Service
public class RoleService {

	private final RoleRepository roleRepository;

	public RoleService(RoleRepository roleRepository) {
		super();
		this.roleRepository = roleRepository;
	}
	
	public Role findByName(String roleName) {
		Role role = roleRepository.findByNameIgnoreCase(roleName);
		
		return role;
	}
}
