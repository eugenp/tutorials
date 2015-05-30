

package org.baeldung.caching.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;



/**
 * The Class CustomerDataService.
 */
@Component

public class SpringCachingBehaviorTest {
/**
 * The main method.
 *
 * @param args the arguments
 */
 public static void main(String[] args) {
    @SuppressWarnings("resource")
    ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
    example = context.getBean(CustomerDataService.class);
    Customer cust = new Customer("Tom", "67-2, Downing Street, NY");
    example.getAddress(cust);
  }

}
