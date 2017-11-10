package com.baeldung.di.constructor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.di.constructor.model.bean.Computer;
import com.baeldung.di.constructor.model.bean.Memory;
import com.baeldung.di.constructor.model.bean.Processor;

@Configuration
public class SpringBeanConstructorContext {
	
	@Bean
	public Computer computer(){
		return new Computer(processor(), memory());
	}
	
	@Bean
	public Processor processor(){
		return new Processor();
	}
	
	@Bean
	public Memory memory(){
		return new Memory();
	}
	
}
