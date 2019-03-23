package com.baeldung.hexagonal.store.infrastructure.persistence.repo;

import com.baeldung.hexagonal.store.core.context.order.entity.Product;
import com.baeldung.hexagonal.store.core.context.order.infrastructure.ProductDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Component
public class ProductRepositoryImpl implements ProductDataStore {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Optional<Product> findById(Long productId) {
        return this.productRepository.findById(productId);
    }
}

@Repository
interface ProductRepository extends CrudRepository<Product, Long> {
}