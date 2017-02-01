package com.baeldung;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PersonDetails {
	
public static void main(String[] args) {
	
	ApplicationContext applicationContext
	      =new ClassPathXmlApplicationContext("applicationContext.xml");
	
	Person person=(Person)applicationContext.getBean("person");
	System.out.println(person);
}
}
