package com.baeldung.domain.ports.dtos;

import java.math.BigDecimal;
import java.util.Objects;

public class Car {
    private Long carId;
    private String name;
    private String brand;
    private BigDecimal minimumPrice;

    public Car(long carId, String name, String brand, BigDecimal minimumPrice) {
        this.carId = carId;
        this.name = name;
        this.brand = brand;
        this.minimumPrice = minimumPrice;
    }

    public Long getCarId() {
        return carId;
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
        Car car = (Car) o;
        return Objects.equals(carId, car.carId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carId);
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }
}
