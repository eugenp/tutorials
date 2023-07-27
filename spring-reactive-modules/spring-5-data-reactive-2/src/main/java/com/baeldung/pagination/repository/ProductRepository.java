package com.baeldung.pagination.repository;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.pagination.model.Product;

import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveSortingRepository<Product, UUID> {
    Flux<Product> findAllBy(Pageable pageable);
}
