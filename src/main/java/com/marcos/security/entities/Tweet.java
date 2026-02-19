package com.marcos.security.entities;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "tweets")
public class Tweet {
	
	public Tweet() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "tweet_id")
	private long tweetId;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	private String content;
	
	//Instant: momento exato captado de algo (considerando fuso-horário)
	@CreationTimestamp
	private Instant creationTimestamp;
	

	public long getTweetId() {
		return tweetId;
	}

	public void setTweetId(long tweetId) {
		this.tweetId = tweetId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Instant getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Instant creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}
	
	
}
