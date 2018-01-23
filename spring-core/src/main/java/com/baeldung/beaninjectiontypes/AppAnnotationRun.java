package com.baeldung.beaninjectiontypes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppAnnotationRun {

	public static void main(String[] args){
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppAnnotationConfig.class);
		Shape shape = ctx.getBean(Shape.class);
		Shape shape1 = ctx.getBean(Shape.class);
		
		shape.setName("Baeldung");
		shape1.setName("Baeldung1");
		
		shape.drawMe();
		shape1.drawMe();
		
	}
}
