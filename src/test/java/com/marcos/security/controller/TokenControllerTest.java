	package com.marcos.security.controller;
	
	import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
	import static org.mockito.Mockito.when;
	import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
	import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
	import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
	import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
	
	import org.junit.jupiter.api.Test;
	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
	import org.springframework.security.oauth2.jwt.JwtEncoder;
	import org.springframework.security.test.context.support.WithMockUser;
	import org.springframework.test.context.bean.override.mockito.MockitoBean;
	import org.springframework.test.web.servlet.MockMvc;
	
	import com.fasterxml.jackson.databind.ObjectMapper;
	import com.marcos.security.dto.LoginRequest;
import com.marcos.security.dto.LoginResponse;
import com.marcos.security.entities.User;
	import com.marcos.security.helper.UserHelper;
	import com.marcos.security.service.TokenService;
	import com.marcos.security.service.UserService;
	
	@WebMvcTest(TokenController.class)
	@AutoConfigureMockMvc(addFilters = false)
	public class TokenControllerTest {
		
		@Autowired
		private MockMvc mvc;
		
		@Autowired
		private ObjectMapper objectMapper;
		
		@MockitoBean
		private UserService userService;
		
		@MockitoBean
		private TokenService tokenService;
		
		@MockitoBean
		private BCryptPasswordEncoder passwordEncoder;
		
		@MockitoBean
		private JwtEncoder jwtEncoder;
		
		
		@Test
		@WithMockUser
		void givenAValidLoginRequest_whenLoginIsCalled_thenTheHttpResponseMustBeOk() throws Exception {
			
			LoginRequest request = new LoginRequest("user", "password");
			
			User user = UserHelper.createValidUser();
			
			LoginResponse response = new LoginResponse("token", 300L);
			
			when(userService.findByUsername(request.username())).thenReturn(Optional.of(user));
			
			when(passwordEncoder.matches(any(), any())).thenReturn(true);
			
			when(tokenService.getJwtValue(any(), any())).thenReturn(response.accessToken());
			
			when(tokenService.getExpiresIn()).thenReturn(response.expiresIn());
			
			mvc.perform(post("/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request))
					.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.accessToken").value(response.accessToken()))
				.andExpect(jsonPath("$.expiresIn").value(response.expiresIn()));
			
		}
		
		@Test
		@WithMockUser
		void given_when_then() throws Exception {
			LoginRequest request = new LoginRequest(" ", " ");
			
			mvc.perform(post("/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request))
					.with(csrf()))
					.andExpect(status().isBadRequest());
	
		}
	}
