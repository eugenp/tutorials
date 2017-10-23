package com.baeldung.spring.annotationTest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.spring.annotationTest.config.AppConfig;
import com.baeldung.spring.annotationTest.services.*; 


public class AppMain {

	public static void main(String[] args) {
		  
			ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
			
			OrderService service = (OrderService)context.getBean("orderService");
			service.saveOrder();
			
	
	}

}
