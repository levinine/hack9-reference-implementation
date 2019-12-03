/*
 * Copyright Levi Nine, 2019
 */
package com.levi9.hack9.reference2019;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Web configuration
 * 
 * @author n.milutinovic
 */
@Configuration
@EnableSwagger2
public class WebConfig implements WebMvcConfigurer {
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
