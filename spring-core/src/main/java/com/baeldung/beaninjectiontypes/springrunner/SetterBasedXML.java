package com.baeldung.beaninjectiontypes.springrunner;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.beaninjectiontypes.setterbased.UserServiceXML;

public class SetterBasedXML {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserServiceXML app = context.getBean(UserServiceXML.class);
		app.processMessage("This is Setter-based bean injection with XML configuration!", "Baeldung", "You@xyz.com");
		context.close();
	}

}
