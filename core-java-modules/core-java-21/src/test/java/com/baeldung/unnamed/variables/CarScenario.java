package com.baeldung.unnamed.variables;

import java.util.List;

class CarScenario {

    protected final List<Car<?>> cars = List.of(
        new Car<>("Mitsubishi", "blue", new GasEngine()),
        new Car<>("Toyota", "red", new ElectricEngine()),
        new Car<>("Jaguar", "white", new HybridEngine())
    );

}
