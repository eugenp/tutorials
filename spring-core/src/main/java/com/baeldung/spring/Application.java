package com.baeldung.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.spring.beans.Car;
import com.baeldung.spring.beans.Truck;

public class Application {

	@SuppressWarnings("resource")
	public static void main(String... args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("springContext.xml");

		// DEPENDENCY INJECTION via CONSTRUCTOR ARGS
		Car car = (Car) context.getBean("car");
		car.create();

		// DEPENDENCY INJECTION via SETTERS
		Truck truck = (Truck) context.getBean("truck");
		truck.create();

	}

}
