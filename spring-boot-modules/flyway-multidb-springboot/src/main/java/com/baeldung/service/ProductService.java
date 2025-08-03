package com.baeldung.service;

import com.baeldung.entity.Product;
import com.baeldung.repository.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    @Transactional("productTransactionManager")
    public void save(Product product) {
        repo.save(product);
    }

    public Optional<Product> findById(Long id) {
        return repo.findById(id);
    }
}
