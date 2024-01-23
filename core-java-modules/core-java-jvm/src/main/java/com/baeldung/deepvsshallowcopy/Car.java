package com.baeldung.deepvsshallowcopy;

import java.util.List;

class Car {
    int numberOfWheels;
    Chasis chasis;
    Engine engine;
    String manufacturerName;
    List<Wheel> wheels;

    //  standard setters and getters
    public Car(int numberOfWheels, Chasis chasis, Engine engine, String manufacturerName, List<Wheel> wheels) {
        this.numberOfWheels = numberOfWheels;
        this.chasis = chasis;
        this.engine = engine;
        this.manufacturerName = manufacturerName;
        this.wheels = wheels;
    }

}
