package com.baeldung.domain.ports.dtos;

import java.math.BigDecimal;
import java.util.Objects;

public class NewCar {
    private String name;
    private String brand;
    private BigDecimal minimumPrice;

    public NewCar(String name, String brand, BigDecimal minimumPrice) {
        this.name = name;
        this.brand = brand;
        this.minimumPrice = minimumPrice;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public BigDecimal getMinimumPrice() {
        return minimumPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewCar newCar = (NewCar) o;
        return Objects.equals(name, newCar.name) &&
                Objects.equals(brand, newCar.brand) &&
                Objects.equals(minimumPrice, newCar.minimumPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, brand, minimumPrice);
    }

    @Override
    public String toString() {
        return "NewCar{" +
                "name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", minimumPrice=" + minimumPrice +
                '}';
    }
}
