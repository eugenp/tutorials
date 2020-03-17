package com.baeldung.staticvalue.injection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("/application.properties")

public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
