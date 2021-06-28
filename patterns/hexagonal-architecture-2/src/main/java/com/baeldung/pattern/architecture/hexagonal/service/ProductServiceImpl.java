package com.baeldung.pattern.architecture.hexagonal.service;

import com.baeldung.pattern.architecture.hexagonal.model.Product;
import com.baeldung.pattern.architecture.hexagonal.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.list();
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Product getById(Long id) {
        return productRepository.get(id);
    }
}