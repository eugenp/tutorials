package com.baeldung.componentscan.filter.annotation;

import java.util.Arrays;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Animal.class))
public class ComponentScanAnnotationFilterApp {
	private static ApplicationContext applicationContext;

	public static void main(String[] args) {
		applicationContext = new AnnotationConfigApplicationContext(ComponentScanAnnotationFilterApp.class);
		Arrays.stream(applicationContext.getBeanDefinitionNames())
				.forEach(System.out::println);
	}
}
