package com.learn.moviecatalogservice.entities;

import java.util.List;

public class UserRating {

	private String UserId;
	private List<Rating> userRating;


	public List<Rating> getUserRating() {
		return userRating;
	}

	public void setUserRating(List<Rating> userRating) {
		this.userRating = userRating;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}
	
	
}
