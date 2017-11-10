package com.baeldung.di.constructor.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages=  { "com.baeldung.di.constructor.model.autowire" })
public class SpringAutowireConstructorContext {
	
}
