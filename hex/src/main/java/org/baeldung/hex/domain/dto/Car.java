package org.baeldung.hex.domain.dto;

import lombok.Data;

@Data
public class Car {
    private String brand;
    private String model;
    private String vinNumber;
    private String plateNumber;
    private String color;
}
