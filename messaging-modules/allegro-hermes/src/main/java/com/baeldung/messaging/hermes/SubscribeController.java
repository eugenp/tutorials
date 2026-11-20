package com.baeldung.messaging.hermes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscribe")
public class SubscribeController {
    private static final Logger LOG = LoggerFactory.getLogger(SubscribeController.class);

    @PostMapping("/testing")
    public void receive(@RequestBody String body) {
        LOG.info("Received message: {}", body);
    }
}
