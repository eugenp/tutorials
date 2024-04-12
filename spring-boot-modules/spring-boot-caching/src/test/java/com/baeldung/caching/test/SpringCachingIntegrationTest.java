package com.baeldung.caching.test;

import com.baeldung.caching.config.CachingConfig;
import com.baeldung.caching.example.Customer;
import com.baeldung.caching.example.CustomerDataService;
import com.baeldung.caching.example.CustomerServiceWithParent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CachingConfig.class }, loader = AnnotationConfigContextLoader.class)
public class SpringCachingIntegrationTest {

    @Autowired
    private CustomerDataService service;

    @Autowired
    private CustomerServiceWithParent serviceWithParent;

    //

    @Test
    public void whenGettingAddress_thenCorrect() {
        final Customer cust = new Customer("Tom", "67-2, Downing Street, NY");
        service.getAddress(cust);
        service.getAddress(cust);

        service.getAddress1(cust);
        service.getAddress1(cust);

        service.getAddress2(cust);
        service.getAddress2(cust);

        service.getAddress3(cust);
        service.getAddress3(cust);

        service.getAddress4(cust);
        service.getAddress4(cust);

        service.getAddress5(cust);
        service.getAddress5(cust);
    }

    @Test
    public void givenUsingServiceWithParent_whenGettingAddress_thenCorrect() {
        final Customer cust = new Customer("Tom", "67-2, Downing Street, NY");

        serviceWithParent.getAddress(cust);
        serviceWithParent.getAddress(cust);

        serviceWithParent.getAddress1(cust);
        serviceWithParent.getAddress1(cust);

        serviceWithParent.getAddress2(cust);
        serviceWithParent.getAddress2(cust);

        serviceWithParent.getAddress3(cust);
        serviceWithParent.getAddress3(cust);

        // serviceWithParent.getAddress4(cust);
        // serviceWithParent.getAddress4(cust);

        serviceWithParent.getAddress5(cust);
        serviceWithParent.getAddress5(cust);
    }

}
