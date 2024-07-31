package com.baeldung.boot.jackson.controller;

import static com.baeldung.boot.jackson.config.CoffeeConstants.FIXED_DATE;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.boot.jackson.model.Coffee;

@RestController
public class CoffeeController {

    @GetMapping("/coffee")
    public Coffee getCoffee(@RequestParam(name = "brand", required = false) String brand,
        @RequestParam(name = "name", required = false) String name) {
        return new Coffee().setBrand(brand)
                .setDate(FIXED_DATE)
                .setName(name);
    }
}
