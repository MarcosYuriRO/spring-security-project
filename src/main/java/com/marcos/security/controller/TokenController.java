package com.marcos.security.controller;

import java.time.Instant;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.marcos.security.dto.LoginRequest;
import com.marcos.security.dto.LoginResponse;
import com.marcos.security.entities.User;
import com.marcos.security.repository.UserRepository;

@RestController
public class TokenController {

	private final JwtEncoder jwtEnconder;
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public TokenController(JwtEncoder jwtEnconder, 
			UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		this.jwtEnconder = jwtEnconder;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
		//A partir do login do usuário, dá a ele umm identificador com oque se pode fazer sem ter que recolocar a senha
		Optional<User> user = userRepository.findByUsername(loginRequest.username());
		
		if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, passwordEncoder)) {
			throw new BadCredentialsException("user or password is invalid");
		}
		
		Instant now = Instant.now();
		long expiresIn = 300L;
		
		var claims = JwtClaimsSet.builder()
				.issuer("security")
				.subject(user.get().getUserId().toString())
				.issuedAt(now)
				.expiresAt(now.plusSeconds(expiresIn))
				.build();
		//claims: informações sobre o usuário
		
		var jwtValue = jwtEnconder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
		//Cria um objeto das claims, a codifica e depois pega o valor do token
		
		return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
				
	}
	
}
