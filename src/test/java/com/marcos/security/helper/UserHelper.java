package com.marcos.security.helper;

import com.marcos.security.entities.User;

public class UserHelper {
	
	

	public static User createValidUser() {
		User user = new User();
		user.setUsername("user");
		user.setPassword("password");
		
		return user;
		
	}
	
}
