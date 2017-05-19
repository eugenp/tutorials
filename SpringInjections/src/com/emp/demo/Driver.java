package com.emp.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Driver {

	private static ApplicationContext ctx;

	public static void main(String args[]){
		ctx = new AnnotationConfigApplicationContext(BeanConfig.class);
		BeanConfig cfg = ctx.getBean(BeanConfig.class);
		System.out.println(cfg.getEmployeeRole());
		Employee emp = (Employee)ctx.getBean("construct");
		System.out.println(emp);
		
	}
	
}
