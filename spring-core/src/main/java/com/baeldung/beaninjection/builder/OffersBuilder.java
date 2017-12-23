package com.baeldung.beaninjection.builder;

import com.baeldung.beaninjection.model.Offers;

public class OffersBuilder {
    public Offers getOffer(int customerId) {
        Offers offers = new Offers(); // Offers to be fetched from backend
        offers.setOfferCode("ABC123");
        offers.setProductRecommendation("Other Products");
        return offers;
    }
}
