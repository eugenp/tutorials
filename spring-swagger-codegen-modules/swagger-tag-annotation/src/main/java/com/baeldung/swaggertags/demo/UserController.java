package com.baeldung.swaggertags.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users/")
@Tag(name = "User Management", description = "Operations related to users")
public class UserController {

    @PostMapping("login")
    public ResponseEntity<String> userLogin() {
        return ResponseEntity.ok("Logged In");
    }

    @Tag(name = "dashboard")
    @GetMapping("profile")
    public ResponseEntity<String> getUserProfile() {
        return ResponseEntity.ok("User Profile");
    }

    @Tag(name = "dashboard")
    @GetMapping("orders")
    public ResponseEntity<String> getUserOrders() {
        return ResponseEntity.ok("User Orders");
    }
}
