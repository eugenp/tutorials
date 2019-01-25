package com.baeldung.hexagonal.repository;

import java.util.Optional;

import com.baeldung.hexagonal.domain.Inventory;

public interface InventoryRepository {

    public Inventory save(Inventory inventory);

    public Optional<Inventory> findInventoryByBookIsbn(String isbn);

    public void delete(Inventory inventory);
}
