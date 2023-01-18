package com.baeldung.shallowvsdeepcopy;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class ShallowAndDeepUnitTest {

    @Test
    public void givenAnObject_whenShallowCopying_thenChangesAppearInBothObjects() throws CloneNotSupportedException {
        HouseLayout layout = new HouseLayout(2, 4);
        ShallowHouse house = new ShallowHouse("123 main street", layout);

        ShallowHouse houseShallowCopy = house.clone();
        houseShallowCopy.getLayout()
          .setFloors(3);

        assertEquals(houseShallowCopy.getLayout()
          .getFloors(), house.getLayout()
          .getFloors());
    }

    @Test
    public void givenAnObject_whenDeepCopying_thenChangesDoNotAppearInBothObjects() throws CloneNotSupportedException {
        HouseLayout layout = new HouseLayout(2, 4);
        DeepHouse house = new DeepHouse("123 main street", layout);

        DeepHouse houseDeepCopy = house.clone();
        houseDeepCopy.getLayout()
          .setFloors(3);

        assertEquals(house.getLayout()
          .getFloors(), 2);
        assertEquals(houseDeepCopy.getLayout()
          .getFloors(), 3);
    }

}
