package com.baeldung.thymeleaf.currencies;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CurrenciesController {

    @GetMapping(value = "/exchange")
    public String exchange(
            @RequestParam(value = "amount") double amount,
            Locale locale) {
        return "currencies/currencies";
    }
}
