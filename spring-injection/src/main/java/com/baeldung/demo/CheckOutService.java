package com.baeldung.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.demo.inventory.InventoryService;
import com.baeldung.demo.profile.ClientProfileService;

@Configuration
@ComponentScan
public class CheckOutService {
    private ClientProfileService clientProfileService = null;
    @Autowired
    private InventoryService inventoryService;

    @Autowired
    public CheckOutService(ClientProfileService clientProfileService) {
        this.clientProfileService = clientProfileService;
    }

    public void checkOut() {
        System.out.printf("Client %S, %S is checking out.", clientProfileService.findByUserId("")
            .getLastName(),
            clientProfileService.findByUserId("")
                .getFirstName());
        System.out.printf("Product Toothbrush has %d left.",inventoryService.returnIntentory("Toothbrush"));
    }

}
