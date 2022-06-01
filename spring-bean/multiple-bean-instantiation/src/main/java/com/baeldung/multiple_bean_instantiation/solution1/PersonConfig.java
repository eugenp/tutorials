package com.baeldung.multiple_bean_instantiation.solution1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonConfig {
	@Bean
	public Person personOne() {
		return new Person("Harold", "Finch");
	}
	
	@Bean
	public Person personTwo() {
		return new Person("John", "Reese");
	}
}
