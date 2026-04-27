package com.marcos.security.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.marcos.security.dto.LoginRequest;
import com.marcos.security.dto.LoginResponse;
import com.marcos.security.entities.User;
import com.marcos.security.service.TokenService;
import com.marcos.security.service.UserService;

import jakarta.validation.Valid;

@RestController
public class TokenController {

	private final UserService userService;
	private final TokenService tokenService;
	private final JwtEncoder jwtEnconder;
	private final BCryptPasswordEncoder passwordEncoder;

	public TokenController(JwtEncoder jwtEnconder,
			BCryptPasswordEncoder passwordEncoder, TokenService tokenService, UserService userService) {
		this.jwtEnconder = jwtEnconder;
		this.userService = userService;
		this.tokenService = tokenService;
		this.passwordEncoder = passwordEncoder;
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest){
		//A partir do login do usuário, dá a ele umm identificador com oque se pode fazer sem ter que recolocar a senha
		Optional<User> user = userService.findByUsername(loginRequest.username());
		
		if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, passwordEncoder)) {
			throw new BadCredentialsException("user or password is invalid");
		}
		
		String jwtValue = tokenService.getJwtValue(user, jwtEnconder);
		
		long expiresIn = tokenService.getExpiresIn();
		
		return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));	
	}
	
}
