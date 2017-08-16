package com.baeldung.drools.service;

import com.baeldung.drools.model.Product;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;


public class ProductServiceIntegrationTest {

    private ProductService productService;

    @Before
    public void setup() {
        productService = new ProductService();
    }


    @Test
    public void whenProductTypeElectronic_ThenLabelBarcode() {
        Product product = new Product("Microwave", "Electronic");
        product = productService.applyLabelToProduct(product);
        assertEquals("BarCode", product.getLabel());
    }

    @Test
    public void whenProductTypeBook_ThenLabelIsbn() {
        Product product = new Product("AutoBiography", "Book");
        product = productService.applyLabelToProduct(product);
        assertEquals("ISBN", product.getLabel());
    }
}
