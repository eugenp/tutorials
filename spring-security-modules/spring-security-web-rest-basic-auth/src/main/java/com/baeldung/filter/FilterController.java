package com.baeldung.filter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilterController {

    @GetMapping("/securityNone/test")
    public String publicEndpoint() {
        return "This is a public endpoint.";
    }

    @GetMapping("/secured/test")
    public String securedEndpoint() {
        return "This is a secured endpoint.";
    }
}
