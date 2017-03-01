/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.beantypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author tehreem.nisa
 */
public class SpringEmployeeRun {

	public static void main(String[] args) {

		// Loading bean property file
		ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("springbeantypes.xml");

		// Constructor based DI Injection

		Employee worker = (Employee) ctx.getBean("Worker");

		worker.show();

		// Setter based DI Injection
		Employee Consultant = (Employee) ctx.getBean("object");

		Consultant.show();

	}

}
