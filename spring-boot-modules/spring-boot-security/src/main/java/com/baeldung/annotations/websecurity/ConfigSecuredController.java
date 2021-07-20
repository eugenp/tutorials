package com.baeldung.annotations.websecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RestController
@EnableWebSecurity
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
