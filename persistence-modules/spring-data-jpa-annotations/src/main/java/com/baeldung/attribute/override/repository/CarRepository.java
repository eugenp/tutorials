package com.baeldung.attribute.override.repository;

import com.baeldung.attribute.override.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer> {
}
