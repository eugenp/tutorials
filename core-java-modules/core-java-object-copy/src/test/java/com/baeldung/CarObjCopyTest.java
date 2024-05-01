package com.baeldung;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CarObjCopyTest {

    @Test
    void testShallowCopy() {
        Car baseObj = new Car(1234, "Toyota", new Car.Engine("V6", 2));
        Car shallowCopy = CarShallowAndDeepCopyFactory.INSTANCE.getShallowCopy(baseObj);
        assertNotSame(baseObj, shallowCopy);
        assertEquals(baseObj.getNumber(), shallowCopy.getNumber());
        assertEquals(baseObj.getName(), shallowCopy.getName());
        assertSame(baseObj.getEngine(), shallowCopy.getEngine());
    }

    @Test
    void testDeepCopy() {
        Car baseObj = new Car(1234, "Toyota", new Car.Engine("V6", 2));
        Car deepCopy = CarShallowAndDeepCopyFactory.INSTANCE.getDeepCopy(baseObj);
        assertNotSame(baseObj, deepCopy);
        assertEquals(baseObj.getNumber(), deepCopy.getNumber());
        assertEquals(baseObj.getName(), deepCopy.getName());
        assertNotSame(baseObj.getEngine(), deepCopy.getEngine());
        assertEquals(baseObj.getEngine()
            .getModel(), deepCopy.getEngine()
            .getModel());
        assertEquals(baseObj.getEngine()
            .getVersion(), deepCopy.getEngine()
            .getVersion());
    }
}