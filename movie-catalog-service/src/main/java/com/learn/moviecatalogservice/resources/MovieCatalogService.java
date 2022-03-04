package com.learn.moviecatalogservice.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.learn.moviecatalogservice.entities.CatalogItem;
import com.learn.moviecatalogservice.entities.UserRating;
import com.learn.moviecatalogservice.services.MovieInfo;
import com.learn.moviecatalogservice.services.UserRatingInfo;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	WebClient.Builder webClientBuilder;
	
	@Autowired
	MovieInfo movieInfo;
	
	@Autowired
	UserRatingInfo userRatingInfo;
	
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog (@PathVariable String userId){
		
		//get all rated movies IDs
		UserRating userRating = userRatingInfo.getUserRating(userId);
		
		//for each movie Id, call movie info service and get details (communication betweenmicroservices)
		return userRating.getUserRating().stream().map(rating -> {
				return movieInfo.getCatalogItem(rating);
		})
		.collect(Collectors.toList());

		//using web client (code inside getCatalogItem
		/*Movie movie=webClientBuilder.build()
			.get()
			.uri("http://localhost:8088/movies/"+rating.getMovieId())
			.retrieve()
			.bodyToMono(Movie.class)
			.block();//wait until the movie object is in the container (mono)
		 */
	}
	

	

}
