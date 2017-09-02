package com.baeldung.differenttypesdi.autowire;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.differenttypesdi.shared.Processor;

@Configuration
@ComponentScan(basePackages={"com.baeldung.differenttypesdi.autowire"})
public class Config {
	
	@Bean
	public Processor processor() {
		return new Processor(2);
	}

}
