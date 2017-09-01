package com.baeldung.differenttypesdi.autowire;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.baeldung.differenttypesdi.autowire.domain.Processor;

@Configuration
@ComponentScan(basePackages={"com.baeldung.differenttypesdi.autowire"})
@ImportResource("classpath:**/differentypes.xml")
public class Config {
	
	@Bean
	public Processor processor() {
		return new Processor(2);
	}

}
