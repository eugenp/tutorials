package com.baeldung.sealed.records;

import org.apache.commons.lang3.StringUtils;

public record Customer(String firstName, String lastName, String customerId) implements Person {

    @Override
    public String getSalutation() {
        return StringUtils.join("Dear customer", " ", firstName);
    }

    @Override
    public String getId() {
        return customerId;
    }

}
