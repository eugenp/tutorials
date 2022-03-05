package com.baeldung.usecase;

import com.baeldung.domain.Product;
import com.baeldung.persistence.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ProductRepositoryImplementation implements ProductRepository {

    private static final Map<Integer, Product> productMap = new HashMap<>(0);

    @Override
    public Product getProductById(Integer productId) {
        return productMap.get(productId);
    }

    @Override
    public Product addProduct(Product product) {
        productMap.put(product.getProductId(), product);
        return product;
    }

    @Override
    public Product removeProduct(Integer productId) {
        if(productMap.get(productId)!= null){
            Product product = productMap.get(productId);
            productMap.remove(productId);
            return product;
        } else
            return null;
    }
}
