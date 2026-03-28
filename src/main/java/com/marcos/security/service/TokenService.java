package com.marcos.security.service;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.marcos.security.entities.Role;
import com.marcos.security.entities.User;

@Service
public class TokenService {
	
	private final long EXPIRES_IN = 300L;
	
	public String getJwtValue(Optional<User> user, JwtEncoder jwtEncoder) {
		Instant now = Instant.now();
		
		String scopes = user.get().getRoles()
				.stream()
				.map(Role::getName)
				.collect(Collectors.joining(" "));
		
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("security")
				.subject(user.get().getUserId().toString())
				.issuedAt(now)
				.expiresAt(now.plusSeconds(EXPIRES_IN))
				.claim("scope", scopes)
				.build();
		//claims: informações sobre o usuário
		
		String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
		//Cria um objeto das claims, a codifica e depois pega o valor do token
		
		return jwtValue;
	}
	
	public long getExpiresIn() {
		return EXPIRES_IN;
	}
	
}
