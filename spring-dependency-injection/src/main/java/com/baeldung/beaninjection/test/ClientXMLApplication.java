package com.baeldung.beaninjection.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.beaninjection.consumer.MyXMLApplication;

public class ClientXMLApplication {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		MyXMLApplication app = context.getBean(MyXMLApplication.class);

		app.processPayments("Account002", 500);

		// close the context
		context.close();
	}

}
