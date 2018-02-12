package com.baeldung.demo.inventory;

import org.springframework.stereotype.Component;

@Component
public class MockInventoryService implements InventoryService {

    public int returnIntentory(String product) {
        return 3;
    }

}
