package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.baeldung.*"})
@SpringBootConfiguration
@Import({SwaggerConfig.class})
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}
}

