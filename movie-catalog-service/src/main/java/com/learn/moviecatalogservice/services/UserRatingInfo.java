package com.learn.moviecatalogservice.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.learn.moviecatalogservice.entities.Rating;
import com.learn.moviecatalogservice.entities.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class UserRatingInfo {

	@Autowired
	private RestTemplate restTemplate;
	

	@HystrixCommand(fallbackMethod ="getFallbackUserRating")
	public UserRating getUserRating(String userId) {
		return restTemplate.getForObject("http://rating-data-service/ratingsdata/users/"+userId, UserRating.class);
	}
	
	private UserRating getFallbackUserRating(String userId) {
		UserRating userRating= new UserRating();
		userRating.setUserId(userId);
		userRating.setUserRating(Arrays.asList(
				new Rating("0",0)));
		return userRating;
	}
}
