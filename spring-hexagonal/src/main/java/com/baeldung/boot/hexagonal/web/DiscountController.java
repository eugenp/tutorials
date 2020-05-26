package com.baeldung.boot.hexagonal.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.boot.hexagonal.app.DiscounterApi;
import com.baeldung.boot.hexagonal.command.DiscountCommand;
import com.baeldung.boot.hexagonal.web.model.DiscountResponse;

@RestController
@RequestMapping("/api/v1")
public class DiscountController {
    private final DiscounterApi discounterApi;

    public DiscountController(DiscounterApi discounterApi) {
        this.discounterApi = discounterApi;
    }

    @GetMapping("/discount")
    public DiscountResponse discount(@RequestParam double amount) {
        DiscountCommand command = new DiscountCommand(amount);

        double discount = discounterApi.discount(command);

        return new DiscountResponse(discount);
    }
}
