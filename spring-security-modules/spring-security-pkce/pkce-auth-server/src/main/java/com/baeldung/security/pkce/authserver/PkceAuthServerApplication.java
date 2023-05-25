package com.baeldung.security.pkce.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PkceAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PkceAuthServerApplication.class, args);
	}

}
