package com.baeldung.patterns.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {

    private String city;
    private String state;
    private String postcode;

}
