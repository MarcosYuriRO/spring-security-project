package com.marcos.security.entities;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.marcos.security.dto.LoginRequest;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "user_id")
	private UUID userId;
	
	@Column(unique = true)
	@NotBlank 
	@NotNull
	private String username;
	
	@NotBlank 
	@NotNull
	private String password;
	
	@Column(name = "user_tweets")
	@OneToMany(mappedBy = "user")
	private List<Tweet> userTweets;
	
	@ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	//Eager: Busca todos os dados das roles do user imediatamente e sempre
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
			)
	//Cria uma tabela referente às roles de determinado usuário
	//Você referenciará nesta tabela o id do usuário e da role como colunas
	private Set<Role> roles;
	
	

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
		return passwordEncoder.matches(loginRequest.password(), this.password);
		//Compara a senha "padrão" do loginRequest, com a senha criptografada no banco de dados.
		
	}
	
	
}
