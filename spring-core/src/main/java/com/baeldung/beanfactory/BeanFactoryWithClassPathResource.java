package com.baeldung.beanfactory;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BeanFactoryWithClassPathResource {

    Resource res = new ClassPathResource("spring-app.xml");
    BeanFactory factory = new XmlBeanFactory(res);

    @Test
    public void createBeanFactory() {

        Employee emp = (Employee) factory.getBean("employee");

        assertFalse(factory.isSingleton("employee"));

        assertTrue(factory.getBean("employee") instanceof Employee);

        assertTrue(factory.isTypeMatch("employee", Employee.class));

        //we have empalias as an alias for employee
        assertTrue(factory.getAliases("employee").length > 0);
    }
}
