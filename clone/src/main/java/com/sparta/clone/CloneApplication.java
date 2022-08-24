package com.sparta.clone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //
public class CloneApplication {

	static {
		System.setProperty("spring.config.location", "/home/ubuntu/application.yml");
	}

	public static void main(String[] args) {
		SpringApplication.run(CloneApplication.class, args);

	}
}
