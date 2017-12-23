package com.baeldung.beaninjection.service;

import org.springframework.stereotype.Component;

import com.baeldung.beaninjection.builder.OffersBuilder;
import com.baeldung.beaninjection.model.Offers;

@Component
public class OffersService {

    public Offers getOffers(int customerId) {
        OffersBuilder offersBuilder = new OffersBuilder();
        return offersBuilder.getOffer(customerId);
    }

}
