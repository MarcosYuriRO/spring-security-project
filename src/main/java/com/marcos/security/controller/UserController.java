package com.marcos.security.controller;

import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.marcos.security.dto.CreateUserDto;
import com.marcos.security.entities.Role;
import com.marcos.security.entities.User;
import com.marcos.security.repository.RoleRepository;
import com.marcos.security.repository.UserRepository;

import jakarta.transaction.Transactional;

@RestController
public class UserController {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	public UserController(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {

		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional
	@PostMapping("/users")
	public ResponseEntity<Void> newUser (@RequestBody CreateUserDto dto){
		
		Role basicRole = roleRepository.findByNameIgnoreCase(Role.Values.BASIC.name());
		
		Optional<User> userFromDb = userRepository.findByUsername(dto.username());
		
		if(userFromDb.isPresent()) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		User user = new User();
		user.setUsername(dto.username());
		user.setPassword(passwordEncoder.encode(dto.password()));
		user.setRoles(Set.of(basicRole));
		
		userRepository.save(user);
		
		return ResponseEntity.ok().build();
	}
}
