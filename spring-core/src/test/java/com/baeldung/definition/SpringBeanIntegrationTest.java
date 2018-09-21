package com.baeldung.definition;

import com.baeldung.definition.domain.Company;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class SpringBeanIntegrationTest {
    @Test
    public void whenUsingIoC_thenDependenciesAreInjected() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Company company = context.getBean("company", Company.class);
        assertEquals("High Street", company.getAddress().getStreet());
        assertEquals(1000, company.getAddress().getNumber());
        assertEquals(1, company.getAccount().getNumber());
        assertEquals(BigDecimal.valueOf(1000.00), company.getAccount().getBalance());
    }
}
