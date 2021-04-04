package com.baeldung.hexagonalarchitecture.adapter.persistance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.baeldung.hexagonalarchitecture.domain.model.Product;
import com.baeldung.hexagonalarchitecture.port.ProductRepository;

/**
 * The interface defines an output adapter which is an implementation of the outbound port. 
 * It enables the core application to communicate to external dependency such as the database.
 */
@Repository
public class ProductRepositoryImplementation implements ProductRepository {

    private static final Map<Integer, Product> productMap = new HashMap<Integer, Product>(0);

    @Override
    public List<Product> getProducts() {
        return new ArrayList<Product>(productMap.values());
    }

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
