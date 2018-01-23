package com.baeldung.difftypesdependencyinjection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author haseeb
 *
 */
@Configuration
@ComponentScan("com.baeldung.difftypesdependencyinjection")
public class Config {

	@Bean
	public Student student() {
		return new Student();
	}
}
