package com.baeldung.hexagonaldraft.adapter.output.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;
import com.baeldung.hexagonaldraft.domain.model.Product;
import com.baeldung.hexagonaldraft.port.outbound.ProductRepository;

@Component
public class DefaultProductRepositoryImplementation implements ProductRepository {

    private static final Map<Integer, Product> productMap = new HashMap<Integer, Product>(0);

    @Override
    public List<Product> getProducts() {
        return new ArrayList<Product>(productMap.values());
    }

    @Override
    public Optional<Product> getProductById(Integer productId) {
        return Optional.ofNullable(productMap.get(productId));
    }

    @Override
    public Product addProduct(Product product) {
        productMap.put(product.getProductId(), product);
        return product;
    }

    @Override
    public Optional<Product> removeProduct(Integer productId) {
        Product product = productMap.get(productId);
        productMap.remove(productId);
        return Optional.ofNullable(product);
    }
}
