package com.baeldung.org;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainBeanInjection {
	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("spconfig.xml");
		JavaContent javaContent = (JavaContent) context.getBean("javaContent");
		javaContent.createBean();

	}
}
