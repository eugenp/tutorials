package com.baeldung.controller;

import com.baeldung.model.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static java.lang.String.format;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public Response test() {
        return new Response(format("Test message... %s.", UUID.randomUUID()));
    }
}
