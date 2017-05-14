package com.baeldung.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.di.model.Author;

@Configuration
@ComponentScan("com.baeldung.di")
public class DiConfig {

	@Bean
	public Author author(){
		return new Author("Baeldung");
	}
}
