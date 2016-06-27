package com.baeldung.spring.web.config.root;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.spring.web.config.LogConfiguration;

@Configuration
@ComponentScan(basePackages = "com.baeldung.spring.web.controller")
public class AppConfig {

	@Bean
	public LogConfiguration logConfiguration() {
		LogConfiguration configuration = new LogConfiguration();
		return configuration;
	}
}
