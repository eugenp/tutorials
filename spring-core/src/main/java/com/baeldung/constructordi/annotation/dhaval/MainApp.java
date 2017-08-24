package com.baeldung.constructordi.annotation.dhaval;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class MainApp {
	public static void main(String[] args) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

		Subject sub = (Subject) context.getBean(Subject.class);
		sub.beginStudy();

		context.close();

	}
}
