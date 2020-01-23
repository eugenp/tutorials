package com.baeldung.boot.configurationproperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionFactory {

	@Bean
	@ConfigurationProperties(prefix = "testing")
	public Connection getTestingConnection() {
		return new Connection();
	}
	
	@Bean
	@ConfigurationProperties(prefix = "live")
	public Connection getLiveConnection() {
		return new Connection();
	}
}
