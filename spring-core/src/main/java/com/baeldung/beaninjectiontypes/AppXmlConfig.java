package com.baeldung.beaninjectiontypes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppXmlConfig {

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("beaninjectiontypes-ctx.xml");
		Shape shape = (Shape) context.getBean("shape");
		Shape shape1 = (Shape) context.getBean(Shape.class);

		shape.setName("Baeldung");
		shape.drawMe();
		shape1.setName("Baeldung1");
		shape.drawMe();
		

	}

}
