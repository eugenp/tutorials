package com.baeldung.spring.transactional.repository;

import com.baeldung.spring.transactional.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
