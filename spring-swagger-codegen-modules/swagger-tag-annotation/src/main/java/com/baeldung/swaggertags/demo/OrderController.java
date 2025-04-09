package com.baeldung.swaggertags.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order Management", description = "Operations related to orders")
public class OrderController {

    @GetMapping
    public ResponseEntity<String> getAllOrders() {
        return ResponseEntity.ok("Order 1");
    }

}
