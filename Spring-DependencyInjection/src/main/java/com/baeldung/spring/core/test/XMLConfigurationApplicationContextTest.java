package com.baeldung.spring.core.test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.spring.core.Department;
import com.baeldung.spring.core.Employee;
import com.baeldung.spring.core.Student;
import com.baeldung.spring.core.Subject;

public class XMLConfigurationApplicationContextTest {

	public static void main(String[] args) {
		AbstractApplicationContext abstractApplicationContext = new ClassPathXmlApplicationContext("beans.xml");
		// Constructor Injection
		Department department = (Department) abstractApplicationContext.getBean("department");
		Employee employee = (Employee) abstractApplicationContext.getBean("employee");
		System.out.println("Department is :: " + department);
		System.out.println("Employee is :: " + employee);

		// Setter Injection
		Subject subject = (Subject) abstractApplicationContext.getBean("subject");
		Student student = (Student) abstractApplicationContext.getBean("student");
		System.out.println("Subject is :: " + subject);
		System.out.println("Student is :: " + student);
		abstractApplicationContext.close();
	}
}
