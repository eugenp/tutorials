package com.baeldung.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.service.CustomerService;

public class UnitTest {

    @Test
    public void whenUsingXmlSetterInjection_thenValueShouldBeJohn() {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContextXML.xml");

        CustomerService customerService = appContext.getBean("customerService", CustomerService.class);

        assertTrue("John".equalsIgnoreCase(customerService.getAllCustomers()
            .get(0)
            .getFirstName()));
    }

    @Test
    public void whenUsingXmlConstInjection_thenValueShouldBeJohn() {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContextXML2.xml");

        CustomerService customerService = appContext.getBean("customerService", CustomerService.class);

        assertTrue("John".equalsIgnoreCase(customerService.getAllCustomers()
            .get(0)
            .getFirstName()));
    }

    @Test
    public void whenUsingAnnoSetterInjection_thenValueShouldBeJohn() {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContextAnno.xml");

        CustomerService customerService = appContext.getBean("customerService", CustomerService.class);

        assertTrue("John".equalsIgnoreCase(customerService.getAllCustomers()
            .get(0)
            .getFirstName()));
    }

    @Test
    public void whenUsingAnnoConstInjection_thenValueShouldBeJohn() {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContextAnno.xml");

        CustomerService customerService = appContext.getBean("customerService", CustomerService.class);

        assertTrue("John".equalsIgnoreCase(customerService.getAllCustomers()
            .get(0)
            .getFirstName()));
    }

}
