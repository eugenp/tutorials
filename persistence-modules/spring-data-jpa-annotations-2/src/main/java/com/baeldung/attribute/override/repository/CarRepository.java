package com.baeldung.attribute.override.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.attribute.override.entity.Car;

public interface CarRepository extends JpaRepository<Car, Integer> {
}
