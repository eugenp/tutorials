package com.baeldung.controller;

import com.baeldung.model.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static java.lang.String.format;

@RestController
public class RetrieveUuidController {

    @GetMapping("/api/uuid")
    public Response uuid() {
        return new Response(format("Test message... %s.", UUID.randomUUID()));
    }
}
