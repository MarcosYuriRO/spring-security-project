package com.marcos.security.entities;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
	
	public User() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "user_id")
	private UUID userId;
	
	@Column(unique = true)
	private String username;
	
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
	
	
}
