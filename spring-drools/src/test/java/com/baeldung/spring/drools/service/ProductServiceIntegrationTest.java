package com.baeldung.spring.drools.service;

import com.baeldung.spring.drools.Application;
import com.baeldung.spring.drools.DroolConfiguration;
import com.baeldung.spring.drools.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DroolConfiguration.class})
public class ProductServiceIntegrationTest {

    @Autowired
    ProductService productService;

    @Test
    public void whenProductTypeElectronic_ThenLabelBarcode(){
        Product product=new Product("Microwave","Electronic");
        product=productService.applyLabelToProduct(product);
        assertEquals("BarCode",product.getLabel());
    }

    @Test
    public void whenProductTypeBook_ThenLabelIsbn(){
        Product product=new Product("AutoBiography","Book");
        product=productService.applyLabelToProduct(product);
        assertEquals("ISBN",product.getLabel());
    }
}
