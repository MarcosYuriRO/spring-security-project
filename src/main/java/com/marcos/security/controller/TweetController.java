package com.marcos.security.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.marcos.security.dto.CreateTweetDto;
import com.marcos.security.entities.Tweet;
import com.marcos.security.entities.User;
import com.marcos.security.repository.TweetRepository;
import com.marcos.security.repository.UserRepository;

@RestController
public class TweetController {

	private final TweetRepository tweetRepository;
	
	private final UserRepository userRepository;

	public TweetController(TweetRepository tweetRepository, UserRepository userRepository) {
		this.tweetRepository = tweetRepository;
		this.userRepository = userRepository;
	}
	
	@PostMapping("/tweets")
	public ResponseEntity<Void> createTweet(@RequestBody CreateTweetDto dto, JwtAuthenticationToken token) {
		
		Optional<User> user = userRepository.findById(UUID.fromString(token.getName()));
		
		Tweet tweet = new Tweet();
		
		tweet.setUser(user.get());
		tweet.setContent(dto.content());
		
		tweetRepository.save(tweet);
		
		return ResponseEntity.ok().build();
		
	}
}
