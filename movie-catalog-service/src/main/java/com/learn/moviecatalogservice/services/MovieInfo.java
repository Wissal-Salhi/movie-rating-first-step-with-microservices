package com.learn.moviecatalogservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.learn.moviecatalogservice.entities.CatalogItem;
import com.learn.moviecatalogservice.entities.Movie;
import com.learn.moviecatalogservice.entities.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class MovieInfo {

	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod ="getFallbackCatalogItem")
	public CatalogItem getCatalogItem(Rating rating) {
		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);// this will unmarshel the result we get into movie object (needs default constructor)                   
		return new CatalogItem(movie.getName(),movie.getDesc(), rating.getRating());
	}
	
	private CatalogItem getFallbackCatalogItem(Rating rating) {
		return new CatalogItem("Movie name not found", "", rating.getRating());
	}
}
