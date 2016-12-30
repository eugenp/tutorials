package com.baeldung.spring.web.config;

import com.baeldung.web.controller.handlermapping.ExampleTwoController;
import com.baeldung.web.controller.handlermapping.WelcomeController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.ControllerClassNameHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class ControllerClassNameHandlerMappingConfig {

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Bean
	public ControllerClassNameHandlerMapping controllerClassNameHandlerMapping() {
		ControllerClassNameHandlerMapping controllerClassNameHandlerMapping = new ControllerClassNameHandlerMapping();
		return controllerClassNameHandlerMapping;
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
