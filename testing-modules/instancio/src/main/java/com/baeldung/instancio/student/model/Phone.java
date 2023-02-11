package com.baeldung.instancio.student.model;

import com.baeldung.instancio.util.PrettyToString;

public class Phone {

    private String countryCode;
    private String number;

    public String getCountryCode() {
        return countryCode;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return PrettyToString.toPrettyString(this);
    }

}
