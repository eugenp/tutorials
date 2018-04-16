package com.baeldung.spring.core.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.baeldung.spring.core.Car;
import com.baeldung.spring.core.Department;
import com.baeldung.spring.core.Employee;
import com.baeldung.spring.core.config.AppConfig;

public class JavaConfigApplicationContextTest {

	public static void main(String[] args) {
		AbstractApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		Department department = applicationContext.getBean(Department.class);
		Employee employee = applicationContext.getBean(Employee.class);
		System.out.println("Department is :: " + department);
		System.out.println("Employee is :: " + employee);
		Car car = applicationContext.getBean(Car.class);
		System.out.println("Car is :: " + car);
		applicationContext.close();
	}
}
