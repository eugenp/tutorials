package com.baeldung.hexagonal.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.baeldung.hexagonal.domain.Product;

public class ProductManager {

    // In memory database
    private Map<Integer, Product> dataStore = new HashMap<>();

    Random rand = new Random();

    public void createProduct(Product p) {
        int id = rand.nextInt(100);
        p.setDiscount(0);
        if (p.getStock() > 0)
            p.setAvailable(true);
        dataStore.putIfAbsent(id, p);
    }

    public Product getProduct(int productId) {
        return dataStore.get(productId);
    }

    public void updateProduct(Product p) {
        dataStore.put(p.getProductId(), p);
    }

    public void updateStock(Product p, int new_stock) {
        p.setStock(p.getStock() + new_stock);
        if (p.getStock() > 0)
            p.setAvailable(true);
        dataStore.put(p.getProductId(), p);
    }

    public List<Product> getAll() {
        return (List<Product>) dataStore.values();
    }

}
