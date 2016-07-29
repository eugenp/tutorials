package com.baeldung.spring.web.config.secondaryBuilding;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.spring.web.config.LogAccessService;

@Configuration
@ComponentScan(basePackages = "com.baeldung.spring.web.controller")
public class SecondaryBuildingConfig {

	@Bean
	public LogAccessService logAccessService() {
		final LogAccessService service = new LogAccessService();
		service.setService("file");
		return service;
	}
}
