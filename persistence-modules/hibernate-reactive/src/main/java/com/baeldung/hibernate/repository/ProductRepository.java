package main.java.com.baeldung.hibernate.repository;

import com.baeldung.hibernate.entities.*;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
    Flux<Product> findAllByCategory(String category);

    @Query("SELECT * FROM products WHERE price > :price")
    Flux<Product> findAllByPriceGreaterThan(double price);
}
