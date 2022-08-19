package com.sparta.clone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CloneApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.yml,"
			+ "classpath:application-aws.yml"
			+ "classpath:application-credentials.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(CloneApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}

}
