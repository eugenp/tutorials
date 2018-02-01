package com.baeldung.beaninjection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@Component
@PropertySource("classpath:application.properties")
public class Config {

	@Value("${printerType}")
	private String printerType;

	@Bean(name = "printer")
	public IPrinter printer(ApplicationContext context) {
		if (printerType.equals("decorator")) {
			return context.getBean(DecoratorPrinter.class);
		} else {
			return context.getBean(ConsolePrinter.class);
		}
	}
}
