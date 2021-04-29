package com.baeldung.attribute.overwrite.repository;

import com.baeldung.attribute.overwrite.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer> {
}
