package com.baeldung.hexagonalarchinjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@PropertySource(value = { "classpath:ddd-layers.properties" })
public class EventServiceApplication {

	public static void main(final String[] args) {
		SpringApplication application = new SpringApplication(EventServiceApplication.class);
		application.run(args);
	}

}
