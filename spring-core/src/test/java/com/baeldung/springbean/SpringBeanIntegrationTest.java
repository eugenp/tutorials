package com.baeldung.springbean;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.springbean.domain.Company;

public class SpringBeanIntegrationTest {
    @Test
    public void whenUsingIoC_thenDependenciesAreInjected() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Company company = context.getBean("company", Company.class);
        assertEquals("High Street", company.getAddress().getStreet());
        assertEquals(1000, company.getAddress().getNumber());
    }
}
