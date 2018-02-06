package com.baeldung.spring.di;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class DepdencyInjectionUnitTest {

    private ApplicationContext context;

    @Before
    public  void oneTimeSetup() {
        context = new AnnotationConfigApplicationContext(DependencyInjectionConfig.class);
    }

    @Test
    public void givenConstructorBasedDI_ThenDependencyValid() {
        EmployeeControllerWithConstructorBasedDI employee = (EmployeeControllerWithConstructorBasedDI) context
                                                            .getBean("employeeControllerWithConstructorBasedDI");
        assertEquals("Employee: A, Department: Management", employee.getEmployeeDetails());
    }

    @Test
    public void givenSetterBasedDI_ThenDependencyValid() {
        EmployeeControllerWithSetterBasedDI employee = (EmployeeControllerWithSetterBasedDI) context
                                                        .getBean("employeeControllerWithSetterBasedDI");
        assertEquals("Employee: A, Department: Management", employee.getEmployeeDetails());
    }
}
