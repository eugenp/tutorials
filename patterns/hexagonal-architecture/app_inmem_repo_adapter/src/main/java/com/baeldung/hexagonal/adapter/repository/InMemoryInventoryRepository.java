package com.baeldung.hexagonal.adapter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.domain.Inventory;
import com.baeldung.hexagonal.repository.InventoryRepository;

@Repository
public interface InMemoryInventoryRepository extends InventoryRepository, CrudRepository<Inventory, Long> {

}
