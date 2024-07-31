package com.baeldung.staticmethod;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Test;

import com.baeldung.staticmodifier.Car;

public class CallNonStaticMethodUnitTest {
    @AfterClass
    public static void setUpCarInstance() {
        Car.setNumberOfCars(0);
    }
    @Test
    public void whenCallingNonStaticMethodInStaticMethodWithInstanceClass_thenSuccess() {
        Car car = new Car("Jaguar", "V8");
        assertEquals("Jaguar-V8", Car.getCarsInformation(car));
    }

}
