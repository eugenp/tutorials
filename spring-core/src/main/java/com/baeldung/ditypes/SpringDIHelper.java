package com.baeldung.ditypes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.constructordi.domain.Car;
import com.baeldung.setterdi.Config;

public class SpringDIHelper {
	public static void main(String[] args) {
		//dependency injection using constructor
		Car tata = getCarConstructorFromXml();

		System.out.println(tata);

		tata = getCarConstructorFromJavaConfig();

		System.out.println(tata);
		
		//dependency injection using setter
		tata = getCarFromXml();

		System.out.println(tata);

		tata = getCarFromJavaConfig();

		System.out.println(tata);
	}

	private static Car getCarConstructorFromJavaConfig() {
		ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

		return context.getBean(Car.class);
	}

	private static Car getCarConstructorFromXml() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"dependencyinjectiontypes-constructor.xml");

		return context.getBean(Car.class);
	}

	private static Car getCarFromJavaConfig() {
		ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

		return context.getBean(Car.class);
	}

	private static Car getCarFromXml() {
		ApplicationContext context = new ClassPathXmlApplicationContext("dependencyinjectiontypes-setter.xml");

		return context.getBean(Car.class);
	}
}
