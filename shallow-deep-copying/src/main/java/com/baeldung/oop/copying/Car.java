package com.baeldung.oop.copying;

/**
 * This class is for demo of Shallow and deep copying.
 * <p>
 * Project Name: Spring boot
 *
 * @author Zahid Khan
 * @since 1.0
 */
record Car(Engine engine) {
    Car getShallowCopy() {

        return new Car(engine());
    }

    Car getDeepCopy() {
        return new Car(new Engine(engine().name()));
    }
}

