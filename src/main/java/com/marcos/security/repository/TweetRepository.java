package com.marcos.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marcos.security.entities.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

}
