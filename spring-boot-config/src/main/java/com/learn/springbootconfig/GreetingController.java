package com.learn.springbootconfig;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	@Value("${my.greeting: default value}")
	private String myGreeting;
	
	@Value("${my.list.values}")
	private List<String> listValues;
	
	@Value("#{${db.values}}")
	private Map<String,String> dbValues;
	
	@Autowired
	private DbSettings dbSettings;
	
	@Autowired
	private Environment env;
	
	@RequestMapping("/greeting")
	public String greeting() {
		return dbSettings.getValues() + " "+ dbSettings.getHost() + " "+ dbSettings.getPort();
		//return myGreeting + " "+ listValues + " "+ dbValues;
	}
	
	@GetMapping("/envdetails")
	public String envDetails() {
		return env.toString();
	}
}
