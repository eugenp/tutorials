package com.baeldung.hibernate.repository;

import com.baeldung.hibernate.entities.*;
import org.springframework.data.jpa.repository.Query;
import reactor.core.publisher.Flux;
import org.springframework.data.repository.reactive.*;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
    Flux<Product> findAllByCategory(String category);

}
