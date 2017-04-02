package com.baeldung.spring.di.constructor;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.spring.di.constructor.model.Order;
import com.baeldung.spring.di.service.CompanyService;
import com.baeldung.spring.di.service.EmployeeService;

public class XmlConfigDITest {

    @Test
    public void testTraditinalConstructorDIConfiguration() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beaninjection-context.xml");
        EmployeeService employeeService = (EmployeeService) context.getBean("employeeService");
        assertNotNull(employeeService.getDao());
        assertThat(employeeService.getDao().getDatasource(), equalTo("Oracle"));
        context.close();
    }

    @Test
    public void testNamespaceConstructorDIConfiguration() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beaninjection-context.xml");
        CompanyService company = (CompanyService) context.getBean("companyService");
        assertNotNull(company.getDao());
        assertThat(company.getDao().getDatasource(), equalTo("Oracle"));
        context.close();
    }
    
    @Test
    public void testTraditinalSetterDIConfiguration() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beaninjection-context.xml");
        Order order = (Order) context.getBean("order");
        assertNotNull(order.getTrade());
        assertThat(order.getTransactionType(), equalTo("SELL"));
        context.close();
    }
    
    @Test
    public void testNamespaceSetterDIConfiguration() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beaninjection-context.xml");
        Order order = (Order) context.getBean("orderBrkr");
        assertNotNull(order.getTrade());
        assertThat(order.getTransactionType(), equalTo("SELL"));
        assertThat(order.getBroker(), equalTo("Citi"));
        context.close();
    }
}
