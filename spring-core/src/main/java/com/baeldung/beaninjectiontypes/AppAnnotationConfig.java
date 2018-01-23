package com.baeldung.beaninjectiontypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppAnnotationConfig {

	@Bean
	@Scope("prototype")
	public Shape shape(){
		return new Shape();
	}
	
	@Bean 
	public Graphics graphics(){
		return new Graphics(surface());
	}
	@Bean 
	public Surface surface(){
		return new Surface();
		
	}
	
	
}
