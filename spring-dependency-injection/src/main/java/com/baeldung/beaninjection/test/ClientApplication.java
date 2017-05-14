package com.baeldung.beaninjection.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.beaninjection.configuration.DIConfiguration;
import com.baeldung.beaninjection.consumer.MyApplication;

public class ClientApplication {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DIConfiguration.class);
		MyApplication app = context.getBean(MyApplication.class);
		
		app.processPayments("Account001", 1000);
		
		//close the context
		context.close();
	}

}
