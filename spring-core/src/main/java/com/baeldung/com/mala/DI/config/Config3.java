package com.mala.DI.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.mala.DI.model.IMedicine;
import com.mala.DI.model.InjectableMedicine;

@Configuration
@ComponentScan("com.mala.DI.model")
public class Config3 {
	@Bean
	public IMedicine getMedicine() {
		return new InjectableMedicine("Levolin.63");	
	}
}
