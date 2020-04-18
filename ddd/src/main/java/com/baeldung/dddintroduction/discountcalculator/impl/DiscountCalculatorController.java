package com.baeldung.dddintroduction.discountcalculator.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/v1/discount-calculator")
@RequiredArgsConstructor
//Example of Primary Adapter
public class DiscountCalculatorController {

    private DiscountCalculatorService discountCalculatorService;

    @GetMapping
    public BigDecimal calculateDiscount(@RequestParam(value = "price") BigDecimal price, @RequestParam(value = "discount") BigDecimal discount) {
        return discountCalculatorService.calculateDiscount(price, discount);
    }
}
