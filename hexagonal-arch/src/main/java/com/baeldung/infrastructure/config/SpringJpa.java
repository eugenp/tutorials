package com.baeldung.infrastructure.config;


import com.baeldung.domain.entities.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringJpa extends CrudRepository<Car, String> {

}
