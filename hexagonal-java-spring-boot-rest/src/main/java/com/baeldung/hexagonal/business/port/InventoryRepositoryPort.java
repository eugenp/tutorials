package com.baeldung.hexagonal.business.port;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.hexagonal.dao.entity.Inventory;

public interface InventoryRepositoryPort extends JpaRepository<Inventory, Long> {

}
