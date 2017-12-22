package com.baeldung.service;

import org.springframework.stereotype.Component;

import com.baeldung.builder.OffersBuilder;
import com.baeldung.model.Offers;

@Component
public class OffersService {

    public Offers getOffers(int customerId) {
        OffersBuilder offersBuilder = new OffersBuilder();
        return offersBuilder.getOffer(customerId);
    }

}
