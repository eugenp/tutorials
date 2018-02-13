package com.baeldung.spring;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.spring.config.ApplicationConfig;
import com.baeldung.spring.service.EmployeeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
public class TestSpringSetterInjection {

	@Autowired
	private EmployeeService employeeService;
	
	@Test
	public void testEmployeeService() {
        Assert.assertTrue(employeeService instanceof EmployeeService);
        employeeService.printEmployeeMessage();
	}
	
}
