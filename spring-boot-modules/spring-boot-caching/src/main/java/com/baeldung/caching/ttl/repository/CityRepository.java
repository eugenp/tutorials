package com.baeldung.caching.ttl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.caching.ttl.model.City;

public interface CityRepository extends JpaRepository<City, Long> {}