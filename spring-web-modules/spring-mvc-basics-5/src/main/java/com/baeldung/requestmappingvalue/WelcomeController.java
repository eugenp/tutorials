package com.baeldung.requestmappingvalue;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/${request.value}")
public class WelcomeController {

    @GetMapping
    public String getWelcomeMessage() {
        return "Welcome to Baeldung!";
    }
}
