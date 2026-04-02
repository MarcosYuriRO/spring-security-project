package com.marcos.security.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import com.marcos.security.dto.LoginRequest;
import com.marcos.security.dto.LoginResponse;
import com.marcos.security.entities.User;
import com.marcos.security.helper.UserHelper;
import com.marcos.security.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class TokenControllerTest {
	
	@Autowired
	TokenController tokenController;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	PasswordEncoder passwordEncoder;
	
	@Mock
	JwtEncoder jwtEncoder;

	@Test
	void givenAValidLoginRequest_whenLoginIsCalled_thenTheHttpResponseMustBeOk() {
		LoginRequest loginRequest = new LoginRequest("user", "password");
		
		Optional<User> user = Optional.of(UserHelper.createValidUser());
		
		when(userRepository.findByUsername(loginRequest.username()))
			.thenReturn(user);
		
		when(user.get().isLoginCorrect(loginRequest, passwordEncoder))
			.thenReturn(true);
		
		Jwt jwt = Mockito.mock(Jwt.class);
		
		when(jwtEncoder.encode(any()))
			.thenReturn(jwt);
		
		when(jwt.getTokenValue())
			.thenReturn("token");
		
		ResponseEntity<LoginResponse> result = tokenController.login(loginRequest);
		
		assertEquals(, result);
		
		
	}
}
