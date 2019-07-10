package com.baeldung.service;

import com.baeldung.model.Product;
import com.baeldung.port.ProductRepositoryPort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ProductService {

    @Autowired
    private ProductRepositoryPort productRepositoryPort;

    public void create(String name, String description, long code){
        productRepositoryPort.create(name, description, code);
    }

    public Product view(Integer productId){
        return productRepositoryPort.getProduct(productId);
    }
}