package com.baeldung.hexagonalarchitecture.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Car {

    private String registrationNumber;
    private String name;
    private String color;
}
