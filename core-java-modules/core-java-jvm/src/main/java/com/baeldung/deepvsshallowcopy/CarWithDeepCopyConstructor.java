package com.baeldung.deepvsshallowcopy;

class CarWithDeepCopyConstructor {
    Engine engine;

    public CarWithDeepCopyConstructor(Engine e) {
        this.engine = e;
    }

    //Deep Copy Constructor
    public CarWithDeepCopyConstructor(CarWithDeepCopyConstructor originalCar) {
        this.engine = new Engine(originalCar.engine.numberOfValves
        , originalCar.engine.numberOfGears);
    }

}
