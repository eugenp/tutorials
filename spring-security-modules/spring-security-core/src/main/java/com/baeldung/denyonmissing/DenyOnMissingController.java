package com.baeldung.denyonmissing;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DenyOnMissingController {
    @GetMapping(path = "hello")
    @PreAuthorize("hasRole('USER')")
    public String hello() {
        return "Hello world!";
    }

    @GetMapping(path = "bye")
    public String bye() {
        return "Bye bye world!";
    }
}
