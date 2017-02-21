package org.baeldung.beaninjection;

import org.baeldung.beaninjection.config.BeanInjectionConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BeanInjectionConfig.class }, loader = AnnotationConfigContextLoader.class)
public class StockServiceTest {
    
    @Autowired
    private StockService stockService;

    @Test
    public void givenService_whenUpdateProduct_thenDaoIsCalled() {
        Product product = new Product();
        product.setId(1);
        product.setName("Product 1");
        product.setQuantity(5);
        
        stockService.updateStock(product);
    }
}
