package com.baeldung.domain.service;

import com.baeldung.domain.port.DataFeeder;
import com.google.inject.Inject;

public class DomainService {

    @Inject
    DataFeeder dataFeeder;

    public void printData() {
        System.out.println(dataFeeder.feedData());
    }

}
