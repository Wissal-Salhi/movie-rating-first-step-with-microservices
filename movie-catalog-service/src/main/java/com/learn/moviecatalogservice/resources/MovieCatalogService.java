package com.learn.moviecatalogservice.resources;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.learn.moviecatalogservice.entities.CatalogItem;
import com.learn.moviecatalogservice.entities.Movie;
import com.learn.moviecatalogservice.entities.Rating;
import com.learn.moviecatalogservice.entities.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	WebClient.Builder webClientBuilder;
	
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog (@PathVariable String userId){
		
		//get all rated movies IDs
		UserRating userRating = restTemplate.getForObject(
				 "http://rating-data-service/ratingsdata/users/"+userId, 
				  UserRating.class);
		
		//for each movie Id, call movie info service and get details (communication betweenmicroservices)
		return userRating.getUserRating().stream().map(rating -> {
			
				//using web client
				/*Movie movie=webClientBuilder.build()
					.get()
					.uri("http://localhost:8088/movies/"+rating.getMovieId())
					.retrieve()
					.bodyToMono(Movie.class)
					.block();//wait until the movie object is in the container (mono)
				 */
			
				Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);// this will unmarshel the result we get into movie object (needs default constructor)                   
				return new CatalogItem(movie.getName(),movie.getName() +" desc", rating.getRating());
		})
		.collect(Collectors.toList());
		
		
		//put them all together
		
	}
}
