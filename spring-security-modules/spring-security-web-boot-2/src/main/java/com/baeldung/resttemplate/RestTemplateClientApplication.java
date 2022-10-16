package com.baeldung.resttemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-defaults.properties")
public class RestTemplateClientApplication {
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(RestTemplateClientApplication.class);
		application.setAdditionalProfiles("ssl-client");
		application.run(args);
	}
}