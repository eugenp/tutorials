package com.baeldung.web.reactive.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebClientController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/resource")
    public Map<String, String> getResource() {
        Map<String, String> response = new HashMap<>();
        response.put("field", "value");
        return response;
    }
}
