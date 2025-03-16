package com.baeldung.currencyconverter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.currencyconverter.service.CurrencyConverterService;

@RestController
@RequestMapping("/api/currency")
public class CurrencyConverterController {
    private final CurrencyConverterService currencyConverterService;

    public CurrencyConverterController(CurrencyConverterService currencyConverterService) {
        this.currencyConverterService = currencyConverterService;
    }

    @GetMapping("/convert")
    public double convertCurrency(@RequestParam String targetCurrency,
                                  @RequestParam double amount) {
        return currencyConverterService.getConvertedAmount(targetCurrency, amount);
    }
}
