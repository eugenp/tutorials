package com.baeldung.pattern.architecture.hexagonal.repository;

import com.baeldung.pattern.architecture.hexagonal.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private Map<Long, Product> productList = new HashMap<Long, Product>();

    public List<Product> list() {
        return new ArrayList<>(productList.values());
    }

    public void save(Product product) {
        productList.put(product.getId(), product);
    }

    public Product get(Long id) {
        return productList.get(id);
    }

}
