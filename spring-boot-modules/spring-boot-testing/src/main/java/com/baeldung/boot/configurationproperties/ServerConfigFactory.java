package com.baeldung.boot.configurationproperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfigFactory {

	@Bean
	@ConfigurationProperties(prefix = "server.default")
	public ServerConfig getDefaultConfigs() {
		return new ServerConfig();
	}
}