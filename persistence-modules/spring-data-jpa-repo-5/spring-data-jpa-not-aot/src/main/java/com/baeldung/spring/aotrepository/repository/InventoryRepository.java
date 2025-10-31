package com.baeldung.spring.aotrepository.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.spring.aotrepository.entity.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Inventory save(Inventory inventory);

    @Transactional(readOnly = true)
    List<Inventory> findAll();

    List<Inventory> findAllById(Iterable<Long> longs);

    @Query(value = "SELECT * FROM INVENTORY", nativeQuery = true)
    List<Inventory> nativeQueryFindAllInventories();

    @Query(value = "SELECT u FROM Inventory u")
    List<Inventory> queryFindAllInventories();
}
