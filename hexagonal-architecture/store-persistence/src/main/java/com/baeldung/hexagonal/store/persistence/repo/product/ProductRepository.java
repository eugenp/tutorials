package com.baeldung.hexagonal.store.persistence.repo.product;

import com.baeldung.hexagonal.store.core.context.order.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
}