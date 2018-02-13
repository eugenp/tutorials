package com.baeldung.spring;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.baeldung.spring.service.EmployeeService;

@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class TestSpringSetterInjection extends AbstractJUnit4SpringContextTests {

	private EmployeeService employeeService;
	
	@Before
	public void execute() {
		employeeService = applicationContext.getBean(EmployeeService.class);
	}
	
	@Test
	public void testEmployeeService() {
        Assert.assertTrue(employeeService instanceof EmployeeService);
        employeeService.printEmployeeMessage();
	}
	
}
