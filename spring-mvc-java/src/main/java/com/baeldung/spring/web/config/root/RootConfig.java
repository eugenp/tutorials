package com.baeldung.spring.web.config.root;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.spring.web.config.LogAccessService;
import com.baeldung.spring.web.config.LogConfiguration;

@Configuration
public class RootConfig {

	@Bean
	public LogConfiguration logConfiguration() {
		final LogConfiguration configuration = new LogConfiguration();
		return configuration;
	}

	@Bean
	public LogAccessService logAccessService() {
		final LogAccessService service = new LogAccessService();
		service.setService("socket");
		return service;
	}
}
