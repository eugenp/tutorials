package com.baeldung.overridebean;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Endpoint {

    private final Service service;

    public Endpoint(Service service) {
        this.service = service;
    }

    @GetMapping("/hello")
    public String helloWorldEndpoint() {
        return service.helloWorld();
    }
}
