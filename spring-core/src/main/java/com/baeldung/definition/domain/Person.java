package com.baeldung.definition.domain;

import lombok.Data;

@Data
public class Person {
    private String fullName;
    private Address address;
}
