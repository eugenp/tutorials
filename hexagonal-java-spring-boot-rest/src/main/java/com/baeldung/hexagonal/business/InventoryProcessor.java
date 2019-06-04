package com.baeldung.hexagonal.business;

import java.util.List;
import java.util.Optional;

import com.baeldung.hexagonal.dao.entity.Inventory;

/**
 * The Interface InventoryFacade.
 */
public interface InventoryProcessor {

    List<Inventory> getAllInventories();

    Optional<Inventory> getInventoryById(Long inventoryId);

    Inventory createInventory(Inventory inventory);

    Boolean deleteInventory(Long inventoryId);

    Inventory updateInventory(Inventory inventory);

}
