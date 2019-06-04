package com.baeldung.hexagonal.api.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.business.InventoryProcessor;
import com.baeldung.hexagonal.business.port.InventoryApiPort;
import com.baeldung.hexagonal.common.model.InventoryModel;
import com.baeldung.hexagonal.dao.entity.Inventory;

@RestController
@RequestMapping("/inventories")
public class InventoryController implements InventoryApiPort {

    @Autowired
    private InventoryProcessor inventoryProcessor;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<InventoryModel> getAllInventories() {
        List<Inventory> inventories = inventoryProcessor.getAllInventories();
        return inventories.stream()
            .map(inventory -> convertToModel(inventory))
            .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public InventoryModel getInventoryById(@PathVariable("id") Long inventoryId) {
        Inventory inventory = inventoryProcessor.getInventoryById(inventoryId)
            .get();
        return convertToModel(inventory);
    }

    @PostMapping
    public InventoryModel createInventory(@RequestBody InventoryModel inventoryModel) {
        Inventory inventory = inventoryProcessor.createInventory(convertToEntity(inventoryModel));
        return convertToModel(inventory);
    }

    private InventoryModel convertToModel(Inventory inventory) {
        return modelMapper.map(inventory, InventoryModel.class);
    }

    private Inventory convertToEntity(InventoryModel inventoryModel) {
        return modelMapper.map(inventoryModel, Inventory.class);
    }

}
