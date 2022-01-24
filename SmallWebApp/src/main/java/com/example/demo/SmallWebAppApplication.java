package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan ({"com.server", "com.server.config"})
public class SmallWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmallWebAppApplication.class, args);
	}

}
