package com.baeldung.spring.swagger.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.spring.swagger.model.SwaggerArray;

public interface Repository extends JpaRepository<SwaggerArray, Integer> {
}
