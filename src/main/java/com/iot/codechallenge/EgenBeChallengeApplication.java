package com.iot.codechallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
/**
 * Tihs is Spring Boot Application Class
 * @author Ashish P. Pande
 * @version 1.0.0
 */
@SpringBootApplication
public class EgenBeChallengeApplication {

	/**
	 * Application Boot Start Main Method
	 * @param args
	 */
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(EgenBeChallengeApplication.class, args);
	}
}
