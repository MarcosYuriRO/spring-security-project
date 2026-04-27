package com.marcos.security.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.marcos.security.service.RoleService;
import com.marcos.security.service.UserService;

@WebMvcTest(TokenController.class)
@AutoConfigureMockMvc
public class UserControllerTest {
	
	@Autowired
	MockMvc mvc;
	
	@MockitoBean
	UserService userService;
	
	@MockitoBean
	RoleService roleService;
	
	
	@Test
	@WithMockUser
	void given_when_then() {
		
	}
}
