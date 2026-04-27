package com.marcos.security.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.marcos.security.dto.CreateUserDto;
import com.marcos.security.entities.Role;
import com.marcos.security.entities.User;
import com.marcos.security.repository.RoleRepository;
import com.marcos.security.repository.UserRepository;
import com.marcos.security.service.RoleService;
import com.marcos.security.service.UserService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
public class UserController {

	private final UserService userService;
	private final RoleService roleService;
	
	public UserController(UserRepository userRepository, 
			RoleRepository roleRepository, 
			RoleService roleService, 
			UserService userService) {
		this.userService = userService;
		this.roleService = roleService;
	}
	
	@Transactional
	@PostMapping("/users")
	public ResponseEntity<Void> newUser (@RequestBody @Valid CreateUserDto dto){
		
		Role basicRole = roleService.findByName(Role.Values.BASIC.name());
		
		Optional<User> existentUser = userService.findByUsername(dto.username());
		
		if(existentUser.isPresent()) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		User user = userService.buildUser(dto.username(), dto.password(), Set.of(basicRole));
		
		userService.save(user);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/users")
	@PreAuthorize("hasAuthority('SCOPE_admin')")
	public ResponseEntity<List<User>> listUsers() {
		List<User> users = userService.findAll();
		
		return ResponseEntity.ok(users);
	}
}
