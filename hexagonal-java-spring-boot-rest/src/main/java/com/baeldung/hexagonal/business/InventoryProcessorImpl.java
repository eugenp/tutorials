package com.baeldung.hexagonal.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.business.port.InventoryRepositoryPort;
import com.baeldung.hexagonal.dao.entity.Inventory;

/**
 * The Class InventoryFacadeImpl.
 */
@Service
public class InventoryProcessorImpl implements InventoryProcessor {

    @Autowired
    private InventoryRepositoryPort inventoryRepositoryPort;

    @Override
    public List<Inventory> getAllInventories() {
        return inventoryRepositoryPort.findAll();
    }

    @Override
    public Optional<Inventory> getInventoryById(Long inventoryId) {
        return inventoryRepositoryPort.findById(inventoryId);
    }

    @Override
    public Inventory createInventory(Inventory inventory) {
        return inventoryRepositoryPort.save(inventory);
    }

    @Override
    public Boolean deleteInventory(Long inventoryId) {
        Optional<Inventory> inventory = inventoryRepositoryPort.findById(inventoryId);
        if (inventory.isPresent()) {
            inventoryRepositoryPort.delete(inventory.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Inventory updateInventory(Inventory inventory) {
        return inventoryRepositoryPort.saveAndFlush(inventory);
    }

}
