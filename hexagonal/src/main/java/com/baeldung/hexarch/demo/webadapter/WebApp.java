package com.baeldung.hexarch.demo.webadapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.baeldung.hexarch.demo"})
public class WebApp {

	public static void main(String[] args) {
		SpringApplication.run(WebApp.class, args);
	}

}
