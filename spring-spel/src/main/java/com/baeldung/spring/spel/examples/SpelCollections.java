package com.baeldung.spring.spel.examples;

import com.baeldung.spring.spel.entity.Car;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("spelCollections")
public class SpelCollections {
    @Value("#{carPark.carsByDriver['Driver1']}")
    private Car driver1Car;
    @Value("#{carPark.carsByDriver['Driver2']}")
    private Car driver2Car;
    @Value("#{carPark.cars[0]}")
    private Car firstCarInPark;
    @Value("#{carPark.cars.size()}")
    private Integer numberOfCarsInPark;

    public Car getDriver1Car() {
        return driver1Car;
    }

    public void setDriver1Car(Car driver1Car) {
        this.driver1Car = driver1Car;
    }

    public Car getDriver2Car() {
        return driver2Car;
    }

    public void setDriver2Car(Car driver2Car) {
        this.driver2Car = driver2Car;
    }

    public Car getFirstCarInPark() {
        return firstCarInPark;
    }

    public void setFirstCarInPark(Car firstCarInPark) {
        this.firstCarInPark = firstCarInPark;
    }

    public Integer getNumberOfCarsInPark() {
        return numberOfCarsInPark;
    }

    public void setNumberOfCarsInPark(Integer numberOfCarsInPark) {
        this.numberOfCarsInPark = numberOfCarsInPark;
    }

    @Override
    public String toString() {
        return "[" +
                "driver1Car=" + driver1Car +
                ", driver2Car=" + driver2Car +
                ", firstCarInPark=" + firstCarInPark +
                ", numberOfCarsInPark=" + numberOfCarsInPark +
                ']';
    }
}
