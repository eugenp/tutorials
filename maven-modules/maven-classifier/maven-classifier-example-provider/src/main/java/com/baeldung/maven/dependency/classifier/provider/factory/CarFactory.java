package com.baeldung.maven.dependency.classifier.provider.factory;

import com.baeldung.maven.dependency.classifier.provider.model.Car;
import com.baeldung.maven.dependency.classifier.provider.model.Car.Type;

public class CarFactory {

    public static Car manufacture(Type carType) {
        Car car = new Car();
        car.setType(carType);

        return car;
    }
}
