package com.example;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.model.Employee;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:core-config/spring-config.xml")
public class EmployeeTest {
	@Autowired
	ApplicationContext context; 
	@Test
	public void testSpringConfigXml_whenSetter_thenInjected() {
		Employee e = context.getBean("employee", Employee.class);
		assertNotNull(e);
		assertNotNull(e.getAddress());
	}
	
	@Test
	public void testSpringConfigXml_whenConstructor_thenInjected() {
		Employee e = context.getBean("employee_constructor", Employee.class);
		assertNotNull(e);
		assertNotNull(e.getAddress());
	}
}
