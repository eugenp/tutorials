package com.mala.DI.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.mala.DI.model.IMedicine;
import com.mala.DI.model.InjectableMedicine;
import com.mala.DI.model.OralMedicine;

@Configuration
@ComponentScan("com.mala.DI.model")
public class Config {
	@Bean
	public IMedicine getMedicine() {
		return new OralMedicine("FastAspirin");	
	}
}
