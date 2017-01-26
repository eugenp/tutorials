package com.baeldung.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.beans.AppConfig;
import com.baeldung.beans.Person;

public class App {
	
	public static void main(String[] args){
		ApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);
		
		Person p = (Person) appContext.getBean("person");
		
		p.getPet().setName("Rudolf");
		System.out.println(p.getName() + " " + p.getAge() + " " + p.getPet().getName());
		((ConfigurableApplicationContext) appContext).close();
	}
}
