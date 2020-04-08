package com.example.jwtapptest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JwtAppTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtAppTestApplication.class, args);
	}

}
