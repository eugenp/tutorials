package com.baeldung.deepcopy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class CarLotTest {

    @Test
    public void shallowCopyIssueTest() throws CloneNotSupportedException {

        Car tesla = new Car("Tesla", 300);
        Car vw = new Car("Passat", 145);
        Car ford = new Car("Mustang", 450);

        CarLot carLot1 = new CarLot(new Car[] { tesla, vw, null });
        CarLot carLot2 = new CarLot(carLot1.getCars());

        assertNotEquals(carLot1, carLot2);
        assertEquals(carLot2.getCars()[2], null);

        carLot1.getCars()[2] = ford;

        assertEquals(carLot2.getCars()[2], ford);
    }

    @Test
    public void deepCopyTest() {
        Car tesla = new Car("Tesla", 300);
        Car vw = new Car("Passat", 145);
        Car ford = new Car("Mustang", 450);

        CarLot carLot1 = new CarLot(new Car[] { tesla, vw, null });
        CarLot carLot2 = new CarLot(carLot1);

        assertNotEquals(carLot1, carLot2);
        assertEquals(carLot2.getCars()[2], null);

        carLot1.getCars()[2] = ford;

        assertEquals(carLot2.getCars()[2], null);
    }
}