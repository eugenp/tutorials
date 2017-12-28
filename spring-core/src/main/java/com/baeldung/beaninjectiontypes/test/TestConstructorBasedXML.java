package com.baeldung.beaninjectiontypes.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.beaninjectiontypes.constructorbased.UserServiceXML;

public class TestConstructorBasedXML {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserServiceXML app = context.getBean(UserServiceXML.class);
		app.processMessage("This is Constructor-based bean injection with XML configuration!", "Baeldung",
				"You@xyz.com");
		context.close();
	}

}
