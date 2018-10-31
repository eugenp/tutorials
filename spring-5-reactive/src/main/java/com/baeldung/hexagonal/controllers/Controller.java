package com.baeldung.hexagonal.controllers;

import com.baeldung.hexagonal.core.model.ExchangeRateDomain;
import com.baeldung.hexagonal.core.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

@RestController("/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class Controller {

    private final ExchangeRateService exchangeRateService;

    @GetMapping("rates")
    public List<ExchangeRateDomain> getRates(@RequestParam(required = false) LocalDate from, @RequestParam(required = false) LocalDate to, @RequestParam(required = false, defaultValue = "USD") String src,
        @RequestParam(required = false, defaultValue = "EUR") String dest) {
        if (from == null) {
            from = LocalDate.of(2018, 1, 1);
        }
        if (to == null) {
            to = LocalDate.now();
        }

        return exchangeRateService.getHistory(from, to, Currency.getInstance(src), Currency.getInstance(dest));
    }

    @PostMapping("rates")
    public ResponseEntity<Object> updateRates(@RequestParam(required = false, defaultValue = "USD") String src, @RequestParam(required = false, defaultValue = "EUR") String dest) {
        ExchangeRateService.UpdateResult updateResult = exchangeRateService.update(Currency.getInstance(src), Currency.getInstance(dest));
        return updateResult.getStatus() == ExchangeRateService.UpdateResult.Status.OK ? ResponseEntity.accepted()
            .body(updateResult.getMessage()) : new ResponseEntity<>(updateResult.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
