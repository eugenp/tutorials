package com.baeldung.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    private Repository repository;

    @PostMapping("/order/{mode}")
    @Transactional
    public void handle(@PathVariable("mode") String mode) {
        repository.updateDatabase();

        if ("order".equals(mode)) {
            throw new RuntimeException("Order Service failed");
        }
    }
}
