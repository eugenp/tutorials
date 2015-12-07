package org.baeldung.caching.test;

import org.baeldung.caching.config.CachingConfig;
import org.baeldung.caching.example.Customer;
import org.baeldung.caching.example.CustomerDataService;
import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@Component
public class SpringCachingBehaviorTest {

    @Test
    public void testCaching() {
        @SuppressWarnings("resource")
        final
        // final ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(CachingConfig.class);
        context.refresh();
        final CustomerDataService service = context.getBean(CustomerDataService.class);

        final Customer cust = new Customer("Tom", "67-2, Downing Street, NY");
        service.getAddress(cust);
        service.getAddress(cust);

        // fail("Unable to instantiate the CustomerDataService");
    }

}
