package com.baeldung.swaggerresponseapi.service;

import com.baeldung.swaggerresponseapi.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    List<Product> productsList = new ArrayList<>();

    public Product addProducts(Product product) {
        productsList.add(product);
        return product;
    }

    public List<Product> getProductsList() {
        return productsList;
    }
}
