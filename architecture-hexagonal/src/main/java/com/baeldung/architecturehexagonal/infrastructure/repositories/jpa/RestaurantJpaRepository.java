package com.baeldung.architecturehexagonal.infrastructure.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.architecturehexagonal.infrastructure.repositories.dao.RestaurantDAO;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<RestaurantDAO, String> {
}
