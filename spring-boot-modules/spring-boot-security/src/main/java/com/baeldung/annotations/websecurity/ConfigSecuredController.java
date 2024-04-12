package com.baeldung.annotations.websecurity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigSecuredController {

    @GetMapping("/public")
    public String publicHello() {
        return "Hello Public";
    }

    @GetMapping("/protected")
    public String protectedHello() {
        return "Hello from protected";
    }

    @GetMapping("/admin")
    public String adminHello() {
        return "Hello from admin";
    }

}
