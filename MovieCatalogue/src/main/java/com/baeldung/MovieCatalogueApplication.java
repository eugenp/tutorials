package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.baeldung")
public class MovieCatalogueApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogueApplication.class, args);
	}

}
