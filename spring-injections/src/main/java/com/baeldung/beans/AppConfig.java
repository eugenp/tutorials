package com.baeldung.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	
	@Bean
	public Person person(){
		return new Person();
	}
	
	@Bean
	public Animal animal(){
		return new Animal();
	}
}
