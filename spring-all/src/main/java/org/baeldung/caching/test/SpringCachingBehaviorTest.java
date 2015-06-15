

package org.baeldung.caching.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import static org.junit.Assert.*;
import org.junit.Test;



@Component

public class SpringCachingBehaviorTest {

 @Test
 public void testCaching() {
    @SuppressWarnings("resource")
    ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
    example = context.getBean(CustomerDataService.class);
    Customer cust = new Customer("Tom", "67-2, Downing Street, NY");
    example.getAddress(cust);
    fail("Unable to instantiate the CustomerDataService");
  }

}
