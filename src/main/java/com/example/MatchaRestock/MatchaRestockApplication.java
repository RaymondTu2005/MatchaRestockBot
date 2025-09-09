package com.example.MatchaRestock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // When using, make sure to put @Scheduled!
@SpringBootApplication
public class MatchaRestockApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchaRestockApplication.class, args);
	}
}
