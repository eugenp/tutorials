package com.baeldung.boot.hexagonal.adapter;

import com.baeldung.boot.hexagonal.core.domain.Product;
import com.baeldung.boot.hexagonal.port.ProductRepo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ProductRepoImpl implements ProductRepo {

    private Map<Long, Product> productDB = new HashMap<Long, Product>();

    @Override
    public void addProduct(Product product) {
        productDB.put(product.getProductId(), product);
    }

    @Override
    public Product getProduct(Long productNumber) {
        return productDB.get(productNumber);
    }

    @Override
    public List<Product> allProducts() {
        return productDB.values().stream().collect(Collectors.toList());
    }
}
