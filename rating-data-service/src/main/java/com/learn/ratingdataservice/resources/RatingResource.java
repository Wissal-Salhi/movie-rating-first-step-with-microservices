package com.learn.ratingdataservice.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.ratingdataservice.entities.Rating;
import com.learn.ratingdataservice.entities.UserRating;

@RestController
@RequestMapping("/ratingsdata")
public class RatingResource {
	
	@RequestMapping("/users/{userId}")
	public UserRating getUserRating(@PathVariable("userId") String userId) {
		List<Rating> ratings = Arrays.asList(
				new Rating("245",10),
				new Rating("900",10),
				new Rating("95",15),
				new Rating("322",124455)
				);
		UserRating userRating = new UserRating();
		userRating.setUserRating(ratings);
		return userRating;
	}
	
	
	@RequestMapping("/{movieId}")
	public Rating getRating(@PathVariable String movieId) {
		return new Rating(movieId,4);
	}

}
