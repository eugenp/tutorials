package com.baeldung.beaninjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class BeanInjectionApplication {

	@Autowired
	private PrinterApp printerApp;

	public static void main(String[] args) {
		ApplicationContext context
				= new AnnotationConfigApplicationContext(BeanInjectionApplication.class);

		BeanInjectionApplication p = context.getBean(BeanInjectionApplication.class);
		p.start();
	}

	private void start() {
		printerApp.print();
	}
}
