package com.baeldung.hexarchitecture.productstore.adapters.rest.controller;

import com.baeldung.hexarchitecture.productstore.adapters.persistence.ProductServiceAdapter;
import com.baeldung.hexarchitecture.productstore.domain.model.Product;
import com.baeldung.hexarchitecture.productstore.ports.ProductUserInterfacePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/")
public class ProductControllerAdapter implements ProductUserInterfacePort {

    @Autowired
    private ProductServiceAdapter productServiceAdapter;

    @Override
    public void addProduct(@RequestBody Product product) {
        productServiceAdapter.addProduct(product);
    }

    @Override
    public Product getProduct(@PathVariable String id) {
        return productServiceAdapter.getProduct(id);
    }
}