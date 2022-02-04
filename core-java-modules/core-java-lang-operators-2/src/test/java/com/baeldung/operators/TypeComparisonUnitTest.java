package com.baeldung.operators;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import com.baeldung.operators.model.Airplane;
import com.baeldung.operators.model.Car;
import com.baeldung.operators.model.Transport;

class TypeComparisonUnitTest {

    @Test
    public void whenCarIsNotTheSameTypeOfAirplane() {
        Object car = new Car();
        assertEquals(car instanceof Airplane, false);

    }

    @Test
    public void whenCarIsATypeOfTransport() {
        Car car = new Car();
        assertEquals(car instanceof Transport, true);

    }

    @Test
    public void whenAirplaneIsATypeOfTransport() {
        Airplane airplane = new Airplane();
        assertEquals(airplane instanceof Transport, true);

    }

    @Test
    public void whenTransportCanBeCar() {
        Transport transport = new Car();
        assertEquals(transport instanceof Car, true);

    }

    @Test
    public void whenTransportCanBeAirplane() {
        Transport transport = new Airplane();
        assertEquals(transport instanceof Airplane, true);

    }

}
