package com.baeldung.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.spring.beans.Car;
import com.baeldung.spring.beans.Truck;

public class Application {

	@SuppressWarnings("resource")
	public static void main(String... args) {

		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

		// DEPENDENCY INJECTION via CONSTRUCTOR ARGS
		Car car = (Car) context.getBean(Car.class);
		car.create();

		// DEPENDENCY INJECTION via AUTOWIRING (SETTER INJECTION)
		Truck truck = (Truck) context.getBean(Truck.class);
		truck.create();

	}

}
