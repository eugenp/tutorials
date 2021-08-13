package com.baeldung.hexagonaldraft.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonaldraft.domain.model.Product;
import com.baeldung.hexagonaldraft.port.inbound.ProductService;
import com.baeldung.hexagonaldraft.port.outbound.ProductRepository;


/**
 * The class is an domain use case implementation of the inbound port.
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
    public Optional<Product> getProductById(Integer productId) {
        return productRepository.getProductById(productId);
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.addProduct(product);
    }

    @Override
    public Optional<Product> removeProduct(Integer productId) {
        return productRepository.removeProduct(productId);
    }
}
