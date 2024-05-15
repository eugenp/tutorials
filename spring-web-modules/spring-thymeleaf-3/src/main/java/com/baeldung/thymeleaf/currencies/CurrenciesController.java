package com.baeldung.thymeleaf.currencies;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CurrenciesController {

    @GetMapping(value = "/currency")
    public String exchange(
      @RequestParam(value = "amount", required = false) String amount,
      @RequestParam(value = "amountList", required = false) List amountList,
      Locale locale) {

        return "currencies/currencies";
    }
}
