package com.baeldung.spring.di.constructor.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.ToString;

@Component
@ToString
@Getter
public class Trade {
    private Deal deal;
    private Stock stock;

    @Autowired
    public Trade(Deal deal, Stock stock) {
        this.deal = deal;
        this.stock = stock;
    }
}
