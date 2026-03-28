package com.marcos.security.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.marcos.security.entities.Role;
import com.marcos.security.entities.User;
import com.marcos.security.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public Optional<User> findByUsername(String username) {
		Optional<User> userFound = userRepository.findByUsername(username);
		
		return userFound;
	}
	
	public Optional<User> findById(UUID id){
		Optional<User> user = userRepository.findById(id);
		return user;
	}
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	public void save(User user) {
		userRepository.save(user);
	}
	
	public User buildUser(String username, String password, Set<Role> roles) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(passwordEncoder.encode(password)));
		user.setRoles(roles);
		
		return user;
	}
}
