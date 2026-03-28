package com.marcos.security.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.marcos.security.dto.FeedItemDto;
import com.marcos.security.entities.Tweet;
import com.marcos.security.entities.User;
import com.marcos.security.repository.TweetRepository;

@Service
public class TweetService {

	private final TweetRepository tweetRepository;

	public TweetService(TweetRepository tweetRepository) {
		this.tweetRepository = tweetRepository;
	}

	public Page<FeedItemDto> findAll(int page, int pageSize) {
		Page<FeedItemDto> tweets = tweetRepository.findAll(PageRequest.of(page, pageSize, Sort.Direction.DESC, "creationTimestamp"))
		.map(tweet -> new FeedItemDto(tweet.getTweetId(), tweet.getContent(), tweet.getUser().getUsername()));
		
		return tweets;
	}
	
	public Optional<Tweet> findById(long id){
		return tweetRepository.findById(id);
	}
	
	public void save (Tweet tweet) {
		tweetRepository.save(tweet);
	}
	
	public void deleteById(long id) {
		tweetRepository.deleteById(id);
	}
	
	public Tweet buildTweet(User user, String content) {
		Tweet tweet = new Tweet();
		
		tweet.setUser(user);
		tweet.setContent(content);
		
		return tweet;
	}
	
	
}
