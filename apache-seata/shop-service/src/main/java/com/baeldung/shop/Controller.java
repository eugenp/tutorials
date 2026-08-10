package com.baeldung.shop;

import org.apache.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    private Repository repository;

    @Autowired
    @Qualifier("inventoryServiceClient")
    private Client inventoryServiceClient;

    @Autowired
    @Qualifier("orderServiceClient")
    private Client orderServiceClient;

    @Autowired
    @Qualifier("billingServiceClient")
    private Client billingServiceClient;

    @PostMapping("/shop/{mode}")
    @GlobalTransactional
    public void handle(@PathVariable("mode") String mode) {
        repository.updateDatabase();
        inventoryServiceClient.callService(mode);
        orderServiceClient.callService(mode);
        billingServiceClient.callService(mode);

        if ("shop".equals(mode)) {
            throw new RuntimeException("Shop Service failed");
        }
    }
}
