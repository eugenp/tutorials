package com.baeldung.patterns.hexagonal.application.adaptor;

import com.baeldung.patterns.hexagonal.application.OutputStrategy;
import com.baeldung.patterns.hexagonal.domain.exception.ProductNotFoundException;
import com.baeldung.patterns.hexagonal.domain.model.Product;
import com.baeldung.patterns.hexagonal.domain.port.ProductService;

public class CommandLineAdaptor {

    private ProductService productService;

    public CommandLineAdaptor(ProductService prodcutService) {
        this.productService = prodcutService;
    }

    public String getProduct(String[] args) {
        Product product = null;
        try {
            product = productService.getProduct(args[0]);
        } catch (ProductNotFoundException e) {
            // Some console specific error handling goes here..
        }
        return product.toString();
    }
}
