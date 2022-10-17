package com.baeldung.boot.count.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.baeldung.boot.count.data.Car;

public interface CarRepository extends MongoRepository<Car, String> {
    @Query(value = "{brand: ?0}", count = true)
    public long countBrand(String brand);

    Long countByBrand(String brand);

    @Query(value = "{}", count = true)
    Long countWithAnnotation();
}
