package com.baeldung.temporal.workflows.sboot.order.services;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InventoryService {

    // Fake inventory. Key is the SKU, value is the quantity.
    private Map<String, InventoryItem> inventory = new ConcurrentHashMap<>();

    public InventoryItem addInventory(String sku, int quantity) {
        return inventory.compute(sku, (k, v) -> {
            if (v == null) {
                return new InventoryItem(sku, quantity, 0);
            } else {
                return new InventoryItem(sku, v.quantity() + quantity, v.reserved());
            }
        });
    }

    public InventoryItem reserveInventory(String sku, int quantity) {
        return inventory.compute(sku, (k, v) -> {
            if (v == null) {
                return new InventoryItem(sku, 0, quantity);
            } else {
                return new InventoryItem(sku, v.quantity(), v.reserved() + quantity);
            }
        });
    }

    public void confirmInventoryReservation(String sku, int quantity) {
        inventory.compute(sku, (k, v) -> {
            if (v == null) {
                return new InventoryItem(sku, 0, 0);
            } else {
                return new InventoryItem(sku, v.quantity() - quantity, v.reserved() - quantity);
            }
        });
    }

    public void cancelInventoryReservation(String sku, int quantity) {
        inventory.compute(sku, (k, v) -> {
            if (v == null) {
                return new InventoryItem(sku, 0, 0);
            } else {
                return new InventoryItem(sku, v.quantity(), v.reserved() - quantity);
            }
        });
    }

    public InventoryItem getInventory(String sku) {
        return inventory.getOrDefault(sku, new InventoryItem(sku, 0, 0));
    }

    static record InventoryItem(String sku, int quantity, int reserved) {

    }
}
