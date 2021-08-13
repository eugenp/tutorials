package com.baeldung.hexagonaldraft.adapter.output.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.hexagonaldraft.domain.model.Product;

@Repository
public interface MySqlSpringDataRepository extends JpaRepository<Product, Integer> {

}