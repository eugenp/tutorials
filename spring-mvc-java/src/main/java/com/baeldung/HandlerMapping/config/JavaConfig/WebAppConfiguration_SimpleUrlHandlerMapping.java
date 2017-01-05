package com.baeldung;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class WebAppConfiguration_SimpleUrlHandlerMapping {

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Bean
	public SimpleUrlHandlerMapping simpleUrlHandlerMapping() {
		SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
		Map<String, Object> urlMap = new HashMap<>();
		urlMap.put("/welcome", welcome());
		urlMap.put("/exampleTwo", exampleTwo());
		simpleUrlHandlerMapping.setUrlMap(urlMap);
		return simpleUrlHandlerMapping;
	}

	@Bean
	public WelcomeController welcome() {
		WelcomeController welcome = new WelcomeController();
		return welcome;
	}

	@Bean
	public ExampleTwoController exampleTwo() {
		ExampleTwoController exampleTwo = new ExampleTwoController();
		return exampleTwo;
	}

}
