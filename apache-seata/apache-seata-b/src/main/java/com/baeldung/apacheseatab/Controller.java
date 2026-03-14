package com.baeldung.apacheseatab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    private Repository repository;

    @Autowired
    private Client client;

    @PostMapping("/b/{mode}")
    @Transactional
    public void handle(@PathVariable("mode") String mode) {
        repository.updateDatabase();
        client.callService(mode);

        if ("b".equals(mode)) {
            throw new RuntimeException("Service B failed");
        }
    }
}
