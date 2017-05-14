/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.beantypes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author tehreem.nisa
 */
public class SpringEmployeeRun {

	public static void main(String[] args) {

		// Loading bean property file
		ApplicationContext context = new AnnotationConfigApplicationContext(EmployeeConfig.class);

		// Constructor based DI Injection

		Employee Manager = (Employee) context.getBean("Manager");
		Manager.show();

		// Setter based DI Injection
		Employee consultant = (Employee) context.getBean("Object");
		consultant.show();

	}

}
