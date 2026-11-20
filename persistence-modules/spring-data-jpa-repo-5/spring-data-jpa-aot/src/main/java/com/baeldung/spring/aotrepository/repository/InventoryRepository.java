package com.baeldung.spring.aotrepository.repository;

import com.baeldung.spring.aotrepository.entity.Inventory;
import com.baeldung.spring.aotrepository.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InventoryRepository extends Repository<Inventory, Long> {

    Inventory save(Inventory inventory);

    @Transactional(readOnly = true)
    List<Inventory> findAll();

    List<Inventory> findAllById(Iterable<Long> longs);

    List<Order> findByProductIdGreaterThan(long productId);

    @Query(value = "SELECT * FROM INVENTORY", nativeQuery = true)
    List<Inventory> nativeQueryFindAllInventories();

    @Query(value = "SELECT u FROM Inventory u")
    List<Inventory> queryFindAllInventories();

    void delete(Inventory entity);
}
