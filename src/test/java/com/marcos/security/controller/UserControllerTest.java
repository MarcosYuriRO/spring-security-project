package com.marcos.security.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcos.security.dto.CreateUserDto;
import com.marcos.security.entities.Role;
import com.marcos.security.entities.User;
import com.marcos.security.service.RoleService;
import com.marcos.security.service.UserService;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {
	
	@Autowired
	MockMvc mvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockitoBean
	UserService userService;
	
	@MockitoBean
	RoleService roleService;
	
	
	@Test
	@WithMockUser
	void givenValidCreateUserDto_whenNewUser_thenReturnHttpStatusOk() throws JsonProcessingException, Exception {
		
		CreateUserDto dto = new CreateUserDto("Marcos", "123");
		
		Role role = new Role();
		role.setRoleId(1);
		role.setName("admin");
		
		when(roleService.findByName(Role.Values.BASIC.name()))
			.thenReturn(role);
		
		Optional<User> userSearched = Optional.empty();
		
		when(userService.findByUsername(dto.username())).thenReturn(userSearched);
		
		User createdUser = new User();
		createdUser.setUsername(dto.username());
		createdUser.setPassword(dto.password());
		createdUser.setRoles(Set.of(role));
		
		when(userService.buildUser(dto.username(), dto.password(), Set.of(role))).thenReturn(createdUser);
		
		mvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))
				.with(csrf()))
			.andExpect(status().isOk());
	}
	
	
}
