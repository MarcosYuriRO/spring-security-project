package com.marcos.security.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.marcos.security.dto.CreateTweetDto;
import com.marcos.security.dto.FeedDto;
import com.marcos.security.dto.FeedItemDto;
import com.marcos.security.entities.Tweet;
import com.marcos.security.entities.User;
import com.marcos.security.repository.TweetRepository;
import com.marcos.security.repository.UserRepository;
import com.marcos.security.service.TweetService;
import com.marcos.security.service.UserService;

import jakarta.validation.Valid;

@RestController
public class TweetController {

	private final TweetService tweetService;
	
	private final UserService userService;
	
	public TweetController(TweetService tweetService, UserService userService) {
		this.tweetService = tweetService;
		this.userService = userService;
	}

	@GetMapping("/feed")
	public ResponseEntity<FeedDto> feed(@RequestParam(value = "page", defaultValue = "0") int page, 
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
		Page<FeedItemDto> tweets = tweetService.findAll(page, pageSize);
		
		return ResponseEntity.ok(new FeedDto(
				tweets.getContent(), page, pageSize,tweets.getTotalPages(), tweets.getTotalElements()));
		
	}
	
	@PostMapping("/tweets")
	public ResponseEntity<Void> createTweet(@RequestBody @Valid CreateTweetDto dto, JwtAuthenticationToken token) {
		
		Optional<User> user = userService.findById(UUID.fromString(token.getName()));
		
		Tweet tweet = tweetService.buildTweet(user.get(), dto.content());
		
		tweetService.save(tweet);
		
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/tweets/{id}")
	public ResponseEntity<Void> deleteTweet(@PathVariable("id") Long tweetId, JwtAuthenticationToken token){
		Optional<User> user = userService.findById(UUID.fromString(token.getName()));
		Tweet tweet = tweetService.findById(tweetId).get();
		
		boolean isAdmin = user.get().getRoles()
			.stream()
			.anyMatch(role -> role.getName().equalsIgnoreCase(com.marcos.security.entities.Role.Values.ADMIN.name()));
		
		if (isAdmin || tweet.getUser().getUserId().equals(UUID.fromString(token.getName()))) {
			tweetService.deleteById(tweetId);
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		
		return ResponseEntity.ok().build();
	}
}
