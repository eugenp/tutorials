package com.baeldung.multiple_bean_instantiation.solution3;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.multiple_bean_instantiation.solution3.Person;

@Configuration
@ComponentScan("com.baeldung.multiple_bean_instantiation.solution3") 
public class PersonConfig {
	/*
	 * @Bean
	 * 
	 * @Qualifier("personOne") public Person personOne() { return new
	 * Person("Harold", "Finch"); }
	 * 
	 * @Bean
	 * 
	 * @Qualifier("personTwo") public Person personTwo() { return new Person("John",
	 * "Reese"); }
	 */
	
	@Bean
	public PersonFactoryPostProcessor PersonFactoryPostProcessor() {
		return new PersonFactoryPostProcessor();
	}
	
	@Bean
	public Human getHuman() {
		/*
		 * Human human = new Human(); human.setPersonOne(personOne);
		 */
		return new Human();
	}
	
	
}
