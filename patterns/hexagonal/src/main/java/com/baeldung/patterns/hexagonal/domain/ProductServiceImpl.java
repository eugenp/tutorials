package com.baeldung.patterns.hexagonal.domain;

import com.baeldung.patterns.hexagonal.domain.exception.ProductNotFoundException;
import com.baeldung.patterns.hexagonal.domain.model.Product;
import com.baeldung.patterns.hexagonal.domain.port.ProductRepository;
import com.baeldung.patterns.hexagonal.domain.port.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void addProducts(List<Product> products) {
        productRepository.addProducts(products);
    }

    @Override
    public Product getProduct(String id) throws ProductNotFoundException {
        Product product = productRepository.findProduct(id);
        if (product == null) {
            throw new ProductNotFoundException();
        }
        return product;
    }
}
