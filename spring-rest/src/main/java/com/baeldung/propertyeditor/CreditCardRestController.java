package com.baeldung.propertyeditor;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/credit-card")
public class CreditCardRestController {

    @GetMapping(value = "/parse/{card-no}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CreditCard parseCreditCardNumber(@PathVariable("card-no") CreditCard creditCard) {
        return creditCard;
    }
}
