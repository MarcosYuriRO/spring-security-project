package com.marcos.security.config;

import java.util.Optional;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.marcos.security.entities.Role;
import com.marcos.security.entities.User;
import com.marcos.security.repository.RoleRepository;
import com.marcos.security.repository.UserRepository;

import jakarta.transaction.Transactional;

@Configuration
public class AdminUserConfig implements CommandLineRunner{

	private RoleRepository roleRepository;
	private UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public AdminUserConfig(RoleRepository roleRepository, UserRepository userRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}



	@Override
	@Transactional
	public void run(String... args) throws Exception {
		Role roleAdmin = roleRepository.findByNameIgnoreCase(Role.Values.ADMIN.name());
		
		Optional<User> userAdmin = userRepository.findByUsername("admin");
		
		userAdmin.ifPresentOrElse(user -> { 
			System.out.println("Admin existente"); 
		}, 
				() -> {
					User user = new User();
					user.setUsername("admin");
					user.setPassword(bCryptPasswordEncoder.encode("123"));
					user.setRoles(Set.of(roleAdmin));
					userRepository.save(user);
				});
	}

}
