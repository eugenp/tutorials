package com.baeldung.beaninjection.beantypes.Services;

import org.springframework.stereotype.Component;

@Component
public class PriceListService {
    private String typeOfPrices;

    public PriceListService(String pricesType) {
        this.typeOfPrices = pricesType;
    }
}
