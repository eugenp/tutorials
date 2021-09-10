package com.example.hexagonalarchitecture.kitchenassistant.domain;

import lombok.NonNull;
import lombok.Value;

@Value
public class Address {

    @NonNull Integer houseNo;

    @NonNull String streetName;

    @NonNull String localGovernment;

    @NonNull String state;

}
