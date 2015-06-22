package org.baeldung.caching.test;

import static org.junit.Assert.fail;

import org.baeldung.caching.example.Customer;
import org.baeldung.caching.example.CustomerDataService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringCachingBehaviorTest {
    @Test
    public void testCaching() {
        @SuppressWarnings("resource")
        final ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
        final CustomerDataService service = context.getBean(CustomerDataService.class);

        final Customer cust = new Customer("Tom", "67-2, Downing Street, NY");
        service.getAddress1(cust);
        fail("Unable to instantiate the CustomerDataService");
    }

}
