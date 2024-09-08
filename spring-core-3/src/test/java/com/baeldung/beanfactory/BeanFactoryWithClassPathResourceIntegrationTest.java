package com.baeldung.beanfactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

public class BeanFactoryWithClassPathResourceIntegrationTest {

    @Test
    public void createBeanFactoryAndCheckEmployeeBean() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions("beanfactory-example.xml");
        Employee emp = (Employee) factory.getBean("employee");

        assertNotNull(emp);
        assertTrue(factory.isSingleton("employee"));
        assertTrue(factory.getBean("employee") instanceof Employee);
        assertTrue(factory.isTypeMatch("employee", Employee.class));
        assertTrue(factory.getAliases("employee").length > 0);
    }
}
