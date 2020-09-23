package com.baeldung.pattern.hexagonal.architecture.adapters.persistence;

import com.baeldung.pattern.hexagonal.architecture.application.domain.Car;
import org.springframework.data.repository.CrudRepository;

public interface SpringDataCarRepository extends CrudRepository<Car, Long> {
}
