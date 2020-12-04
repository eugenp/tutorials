package com.baeldung.boot.jackson.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.boot.jackson.model.Coffee;

@RestController
public class CoffeeController {

    @GetMapping("/coffee")
    public Coffee getCoffee(@RequestParam(required = false) String brand, @RequestParam(required = false) String name) {
        return new Coffee().setBrand(brand)
            .setDate(LocalDateTime.now())
            .setName(name);
    }
}
