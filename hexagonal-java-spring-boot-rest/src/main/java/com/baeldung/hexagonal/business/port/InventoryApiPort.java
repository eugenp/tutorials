package com.baeldung.hexagonal.business.port;

import java.util.List;

import com.baeldung.hexagonal.common.model.InventoryModel;

public interface InventoryApiPort {

    List<InventoryModel> getAllInventories();

    InventoryModel getInventoryById(Long inventoryId);

    InventoryModel createInventory(InventoryModel inventoryModel);

}
