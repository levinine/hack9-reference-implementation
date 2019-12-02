/*
 * Copyright Levi Nine, 2019
 */
package com.levi9.hack9.judge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Skeleton Judge application, just props to receive callback submissions.
 * 
 * @author n.milutinovic
 */
@SpringBootApplication
public class Application {
	/**
	 * Startup SPring Boot application.
	 * 
	 * @param args CLI arguments.
	 */
	public static void main(String... args) {
		SpringApplication.run(Application.class, args);
	}
}
