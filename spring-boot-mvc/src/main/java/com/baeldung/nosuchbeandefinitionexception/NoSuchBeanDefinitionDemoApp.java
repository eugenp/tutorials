package com.baeldung.nosuchbeandefinitionexception;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NoSuchBeanDefinitionDemoApp {

	public static void main(String[] args) {
		SpringApplication.run(NoSuchBeanDefinitionDemoApp.class, args);
	}
}
