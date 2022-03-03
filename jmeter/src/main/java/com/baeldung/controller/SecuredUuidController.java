package com.baeldung.controller;

import static java.lang.String.format;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.model.Response;

@RestController
public class SecuredUuidController {
      
    private static final Logger logger = LoggerFactory.getLogger(SecuredUuidController.class);

    @GetMapping("/secured/uuid")
    public Response uuid() {
        
        logger.info("Returning response");
        
        return new Response(format("Secured test message... %s.", UUID.randomUUID()));
    }
}
