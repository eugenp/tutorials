package com.baeldung.spring_dependency_injection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

	@Bean(name="personJavaConstructor")
	public Person getPersonJavaConstructor() {
		Person personJavaConstructor = new Person("James",24);
		return personJavaConstructor;
	}

	@Bean(name="personJavaSetter")
	public Person getPersonJavaSetter() {
		Person personJavaSetter = new Person();
		personJavaSetter.setName("John");
		personJavaSetter.setAge(25);
		return personJavaSetter;
	}
	
}
