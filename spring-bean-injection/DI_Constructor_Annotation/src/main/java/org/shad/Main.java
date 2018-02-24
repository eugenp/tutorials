package org.shad;

import org.shad.entity.Car;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

	 Car car = (Car) context.getBean("car");
		 
		 car.displayDetails();	
	}
	
	

}
