package com.baeldung.hexarch.adapters.web;

import com.baeldung.hexarch.adapters.request.SwipeRequest;
import com.baeldung.hexarch.application.port.incoming.PaymentCase;
import com.baeldung.hexarch.application.port.incoming.SwipeCase;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/card")
public class CardController {

    private final PaymentCase paymentCase;
    private final SwipeCase swipeCase;

    @PostMapping(value = "/{id}/pay/{amount}")
    void pay(@PathVariable final Long id, @PathVariable final BigDecimal amount) {
        paymentCase.pay(id, amount);
    }

    @PostMapping(value = "/{id}/swipe")
    void swipe(@PathVariable final Long id, @RequestBody SwipeRequest swipeRequest) {
        swipeCase.swipe(id, swipeRequest.getAmount(), swipeRequest.isCash());
    }
}
