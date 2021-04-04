package com.baeldung.hexagonalarchitecture.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonalarchitecture.domain.model.Product;
import com.baeldung.hexagonalarchitecture.port.ProductRepository;
import com.baeldung.hexagonalarchitecture.port.ProductService;

/**
 * The class is an use case implementation of the inbound port.
 */
@Service
public class ProductServiceImplementation implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getProducts() {
        return productRepository.getProducts();
    }

    @Override
    public Product getProductById(Integer productId) {
        return productRepository.getProductById(productId);
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.addProduct(product);
    }

    @Override
    public Product removeProduct(Integer productId) {
        return productRepository.removeProduct(productId);
    }
}
