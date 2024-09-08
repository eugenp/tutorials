package com.baeldung.beanfactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanFactoryWithClassPathResourceIntegrationTest {

    @Test
    public void createBeanFactoryAndCheckEmployeeBean() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("beanfactory-example.xml");
        BeanFactory factory = applicationContext.getBeanFactory();
        Employee emp = (Employee) factory.getBean("employee");

        assertNotNull(emp);
        assertTrue(factory.isSingleton("employee"));
        assertTrue(factory.getBean("employee") instanceof Employee);
        assertTrue(factory.isTypeMatch("employee", Employee.class));
        assertTrue(factory.getAliases("employee").length > 0);
    }
}
