package com.marcos.security;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.marcos.security.dto.LoginRequest;
import com.marcos.security.entities.User;

class UserTest {

    @Test
    void testIsLoginCorrect_WhenTheLoginRequestPasswordIsTheSameAsTheUsersPassword_ThenReturnTrue() {
        User user = new User();
        user.setPassword("encryptedPassword");
        
        LoginRequest loginRequest = new LoginRequest("user", "12345");

        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);

        when(passwordEncoder.matches(loginRequest.password(), user.getPassword()))
            .thenReturn(true);

        boolean result = user.isLoginCorrect(loginRequest, passwordEncoder);

        assertTrue(result, "Should return true when passwords match");
    }

    @Test
    void testIsLoginCorrect_WhenTheLoginRequestPasswordIsntTheSameAsTheUsersPassword_ThenReturnFalse() {
        User user = new User();
        user.setPassword("encryptedPassword");
        
        LoginRequest loginRequest = new LoginRequest("user", "1234");
        
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        
        when(passwordEncoder.matches(loginRequest.password(), user.getPassword()))
            .thenReturn(false);

        boolean result = user.isLoginCorrect(loginRequest, passwordEncoder);

        assertFalse(result, "Should return false when passwords doesn't matches");
    }
}