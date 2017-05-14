package com.baeldung.beaninjection.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutomatedTellerMachine {

    private Transaction transaction;
    private Advertisement ad;

    @Autowired
    public AutomatedTellerMachine(Transaction transaction) {
        this.transaction = transaction;
    }

    @Autowired
    public void setAdvertisement(Advertisement ad) {
        this.ad = ad;
    }

    @Override
    public String toString() {
        return String.format("Transaction: %s Advertisement: %s ", transaction, ad);
    }
}
