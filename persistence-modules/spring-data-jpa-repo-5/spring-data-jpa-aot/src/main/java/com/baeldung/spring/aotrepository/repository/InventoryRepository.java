package com.baeldung.spring.aotrepository.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.spring.aotrepository.entity.Inventory;

public interface InventoryRepository extends Repository<Inventory, Long> {

    Inventory save(Inventory inventory);

    @Transactional(readOnly = true)
    List<Inventory> findAll();

    List<Inventory> findAllById(Iterable<Long> longs);

    @Query(value = "SELECT * FROM INVENTORY", nativeQuery = true)
    List<Inventory> nativeQueryFindAllInventories();

    @Query(value = "SELECT u FROM Inventory u")
    List<Inventory> queryFindAllInventories();
}
