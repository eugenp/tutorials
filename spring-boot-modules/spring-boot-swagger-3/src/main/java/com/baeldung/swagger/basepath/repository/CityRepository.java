package com.baeldung.swagger.basepath.repository;

import com.baeldung.swagger.basepath.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {}
