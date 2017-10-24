package com.baeldung.spring.XMLTest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.spring.XMLTest.OrderService;


public class AppMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

			ApplicationContext context = new ClassPathXmlApplicationContext("BeanInjectionContext.xml");
			
			OrderService orderService = (OrderService)context.getBean("orderService");
			orderService.saveOrder();
			
	}

}
