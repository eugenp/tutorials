package com.baeldung.tokenexchange.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServerApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(AuthServerApplication.class);
		application.setAdditionalProfiles("authserver");
		application.run(args);
	}

}
