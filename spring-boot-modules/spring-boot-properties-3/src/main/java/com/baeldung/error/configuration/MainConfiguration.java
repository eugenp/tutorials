package com.baeldung.error.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <h2>This class is for Configuration.</h2>
 * <p>
 * Project Name: New folder
 *
 * @author Zahid Khan
 * @version 4/1/2023
 */
@Configuration
@ConfigurationProperties(prefix = "profile.application-properties")
public class MainConfiguration {
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
}
