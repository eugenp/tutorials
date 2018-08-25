package com.baeldung.dependency.exception.app;

import com.baeldung.dependency.exception.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseDeptService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public InventoryRepository getInventoryRepository() {
        return inventoryRepository;
    }
}
