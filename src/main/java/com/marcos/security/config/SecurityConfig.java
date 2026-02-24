package com.marcos.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	private SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
			.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
			//Define que toda requisição tem que ser autenticada.
			.csrf(csrf -> csrf.disable())
			//Recomendado apenas localmente/para testes
			.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
			//Utilizará as configurações padrões do JWT
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
			//Define a sessão como 'sem estado'
		
		return http.build();
		//http.build joga uma exception que pode ser tratada na declaração do método
	}
}
