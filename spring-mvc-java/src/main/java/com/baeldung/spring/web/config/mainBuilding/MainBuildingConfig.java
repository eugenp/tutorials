package com.baeldung.spring.web.config.mainBuilding;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.spring.web.config.LogAccessService;

@Configuration
@ComponentScan(basePackages = "com.baeldung.spring.web.controller")
public class MainBuildingConfig {

	@Bean
	public LogAccessService logAccessService() {
		final LogAccessService service = new LogAccessService();
		service.setService("socket");
		return service;
	}
}
