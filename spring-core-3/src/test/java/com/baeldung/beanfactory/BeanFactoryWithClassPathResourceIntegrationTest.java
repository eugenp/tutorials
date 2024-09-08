package com.baeldung.beanfactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanFactoryWithClassPathResourceIntegrationTest {

    @Test
    public void createBeanFactoryAndCheckEmployeeBean() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beanfactory-example.xml");
        Employee emp = (Employee) applicationContext.getBean("employee");

        assertNotNull(emp);
        assertTrue(applicationContext.isSingleton("employee"));
        assertTrue(applicationContext.getBean("employee") instanceof Employee);
        assertTrue(applicationContext.isTypeMatch("employee", Employee.class));
        assertTrue(applicationContext.getAliases("employee").length > 0);
    }
}
