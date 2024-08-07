package com.baeldung.ddd.hexagonal.app.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.ddd.hexagonal.app.domain.Rental;

public interface JpaRentalRepository extends JpaRepository<Rental, Long> {
}
