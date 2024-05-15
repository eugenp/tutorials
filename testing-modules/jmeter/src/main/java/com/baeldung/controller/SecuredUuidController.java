package com.baeldung.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.model.Response;

@RestController
public class SecuredUuidController {
      
    private static final Logger LOGGER = LoggerFactory.getLogger(SecuredUuidController.class);

    @GetMapping("/secured/uuid")
    public Response uuid() {
        
        LOGGER.info("Returning response");
        
        return new Response(String.format("Secured test message... %s.", UUID.randomUUID()));
    }
}
