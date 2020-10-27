package com.baeldung.boot.jackson.controller;

import com.baeldung.boot.jackson.model.Coffee;
import com.baeldung.boot.jackson.model.CoffeeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class CoffeeController {

    @GetMapping("/coffee")
    public CoffeeResponse<Coffee> createCoffee(@RequestParam(required = false) String brand,
                                               @RequestParam(required = false) String name) {
        Coffee coffee = new Coffee()
                .setBrand(brand)
                .setName(name);

        return new CoffeeResponse<Coffee>()
                .setDate(LocalDateTime.now())
                .setBody(coffee);
    }
}
