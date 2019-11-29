/*
 * Copyrgith Levi Nine, 2019
 */
package com.levi9.hack9.reference2019;

import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.Module;

/**
 * Spring Boot application.
 * 
 * @author n.milutinovic
 */
@SpringBootApplication
public class Application {
	/**
	 * Start Spring Boot application.
	 * 
	 * @param args CLI arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
    public Module jsonNullableModule() {
        return new JsonNullableModule();
    }
}
