package com.baeldung;

public class CarShallowAndDeepCopyFactory {

    public static CarShallowAndDeepCopyFactory INSTANCE = new CarShallowAndDeepCopyFactory();

    public Car getShallowCopy(Car baseObj) {
        Car shallowCopy = new Car();
        shallowCopy.number = baseObj.number;
        shallowCopy.engine = baseObj.engine;
        return shallowCopy;
    }

    public Car getDeepCopy(Car baseObj) {
        Car deepCopy = new Car();
        deepCopy.number = baseObj.number;
        deepCopy.engine = new Car.Engine();
        deepCopy.engine.model = new String(baseObj.engine.model);
        deepCopy.engine.version = baseObj.engine.version;
        return deepCopy;
    }

}
