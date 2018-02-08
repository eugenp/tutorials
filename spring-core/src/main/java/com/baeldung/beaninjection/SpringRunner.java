package com.baeldung.beaninjection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.baeldung.beaninjection.constructor.Employee;

@SpringBootApplication
public class SpringRunner {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringRunner.class, args);
		Employee contructorInjection = context.getBean(Employee.class);
		System.out.println(contructorInjection);
		com.baeldung.beaninjection.setter.Employee setterInjection = context.getBean(com.baeldung.beaninjection.setter.Employee.class);
		System.out.println(setterInjection);
		com.baeldung.beaninjection.field.Employee fieldInjection = context.getBean(com.baeldung.beaninjection.field.Employee.class);
		System.out.println(fieldInjection);
	}
}
