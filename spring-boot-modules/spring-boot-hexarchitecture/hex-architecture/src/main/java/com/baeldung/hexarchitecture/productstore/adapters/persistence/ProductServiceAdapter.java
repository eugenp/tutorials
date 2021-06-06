package com.baeldung.hexarchitecture.productstore.adapters.persistence;

import com.baeldung.hexarchitecture.productstore.domain.model.Product;
import com.baeldung.hexarchitecture.productstore.ports.ProductDatabasePort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ProductServiceAdapter implements ProductDatabasePort {

    private Map<String, Product> productDataBase = new HashMap<>();

    @Override
    public void addProduct(Product product) {
        String id = UUID.randomUUID().toString();
        product.setId(id);
        productDataBase.put(id, product);
    }

    @Override
    public Product getProduct(String id) {
        return productDataBase.get(id);
    }
}
