package com.baeldung.boot.hexagonal.core.impl;

import com.baeldung.boot.hexagonal.core.domain.Product;
import com.baeldung.boot.hexagonal.core.port.ProductRepo;
import com.baeldung.boot.hexagonal.core.port.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepository;

    public ProductServiceImpl(ProductRepo productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void addProduct(Product product) {
        productRepository.addProduct(product);
    }

    @Override
    public Product getProduct(Long productNumber) {
        return productRepository.getProduct(productNumber);
    }

    @Override
    public List<Product> allProducts() {
        return productRepository.allProducts();
    }
}
