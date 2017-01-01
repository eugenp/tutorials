package com.baeldung.spring.web.config;

import com.baeldung.web.controller.handlermapping.ExampleTwoController;
import com.baeldung.web.controller.handlermapping.WelcomeController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.springframework.web.servlet.mvc.support.ControllerClassNameHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class BeanNameHandlerMappingConfig {

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Bean
	public BeanNameUrlHandlerMapping controllerClassNameHandlerMapping() {
		BeanNameUrlHandlerMapping beanNameUrlHandlerMapping = new BeanNameUrlHandlerMapping();
		return beanNameUrlHandlerMapping;
	}

	@Bean(name="/welcomeBean")
	public WelcomeController welcomeBean() {
		WelcomeController welcome = new WelcomeController();
		return welcome;
	}

	@Bean(name="/ex")
	public ExampleTwoController exampleTwo() {
		ExampleTwoController exampleTwo = new ExampleTwoController();
		return exampleTwo;
	}

}
