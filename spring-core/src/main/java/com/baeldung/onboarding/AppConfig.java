package com.baeldung.onboarding;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean 
	public Name name() {
		Name name = new Name();
		name.setFirstName("John");
		name.setLastName("Wick");
		return name;
	}
	
	@Bean
	public Person person() {
		return new Person(name());
	}
}
