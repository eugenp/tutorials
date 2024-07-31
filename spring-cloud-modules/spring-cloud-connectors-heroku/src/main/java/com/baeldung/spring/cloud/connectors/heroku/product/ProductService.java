package com.baeldung.spring.cloud.connectors.heroku.product;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> findProductById(Long productId) {
        return productRepository.findById(productId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Product createProduct(Product product) {
        Product newProduct = new Product();
        newProduct.setSku(product.getSku());
        return productRepository.save(newProduct);
    }
}
