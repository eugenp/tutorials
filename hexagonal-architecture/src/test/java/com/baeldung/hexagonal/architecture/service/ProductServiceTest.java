/**
 * 
 */
package com.baeldung.hexagonal.architecture.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.hexagonal.architecture.App;
import com.baeldung.hexagonal.architecture.model.Product;
import com.baeldung.hexagonal.architecture.service.ProductService;

/**
 * @author AshwiniKeshri
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
@ActiveProfiles(value = "test")
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testCreateProduct() {
        Product product = new Product();
        product.setDescription("test product");
        product.setName("Product1");
        product.setPrice(10.0);
        product.setQuantity(100l);
        Long id = productService.create(product);
        Assert.assertTrue(id > 0);
    }

}
