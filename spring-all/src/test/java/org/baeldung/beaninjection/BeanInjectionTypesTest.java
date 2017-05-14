package org.baeldung.beaninjection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:beaninjection-config.xml")
public class BeanInjectionTypesTest
  implements ApplicationContextAware {

    private ApplicationContext context;
    
    @Test
    public void whenSendingNotification_thenMessageIsDelivered() {
        DeployService deployService
          = context.getBean(DeployService.class);
        deployService.deploy();
    }
    
    @Test
    public void whenUpdateProduct_thenDaoIsCalled() {
        Product product = new Product();
        product.setId(1);
        product.setName("Product 1");
        product.setQuantity(5);
        
        StockService stockService
          = context.getBean(StockService.class);
        stockService.updateStock(product);
    }
    
    @Override
    public void setApplicationContext(
      ApplicationContext context) throws BeansException {
        this.context = context;
    }
    
}
