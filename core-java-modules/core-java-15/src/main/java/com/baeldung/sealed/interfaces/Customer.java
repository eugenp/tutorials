package com.baeldung.sealed.interfaces;

public non-sealed interface Customer extends Person {

    int getCustomerId();
    int getNumberOfPurchases();

}
