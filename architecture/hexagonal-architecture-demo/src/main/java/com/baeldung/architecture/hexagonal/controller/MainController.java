package com.baeldung.architecture.hexagonal.controller;

import com.baeldung.architecture.hexagonal.service.Calculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @Autowired
    private Calculator calculator;

    @GetMapping("/add")
    public String add(@RequestParam Double a, @RequestParam Double b) {
        return String.format("Sum of %s and %s is %s", a, b, calculator.add(a, b));
    }

    @GetMapping("/divide")
    public String divide(@RequestParam Double a, @RequestParam Double b) {
        return String.format("%s divides %s by %s", b, a, calculator.divide(a, b));
    }
}
