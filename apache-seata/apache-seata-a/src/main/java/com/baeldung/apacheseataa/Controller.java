package com.baeldung.apacheseataa;

import org.apache.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    private Repository repository;

    @Autowired
    private Client client;

    @PostMapping("/a/{mode}")
    @GlobalTransactional
    public void handle(@PathVariable("mode") String mode) {
        repository.updateDatabase();
        client.callService(mode);

        if ("a".equals(mode)) {
            throw new RuntimeException("Service A failed");
        }
    }
}
