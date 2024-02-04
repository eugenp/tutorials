package com.baeldung.deepvsshallowcopy;

class CarWithShallowCopyConstructor {
    Engine engine;

    public CarWithShallowCopyConstructor(Engine e) {
        this.engine = e;
    }

    //Shallow Copy Constructor
    public CarWithShallowCopyConstructor(CarWithShallowCopyConstructor originalCar) {
        this.engine = originalCar.engine;
    }

}
