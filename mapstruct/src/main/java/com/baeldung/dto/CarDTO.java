package com.baeldung.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarDTO {
    private int id;
    private String name;
    private FuelType fuelType;
}
