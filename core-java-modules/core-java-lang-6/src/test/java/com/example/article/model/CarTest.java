package com.example.article.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarTest {

    @Test
    public void testShallowCopyObject() throws CloneNotSupportedException {
        List<String> accessories = new ArrayList<>();
        accessories.add("item1");
        accessories.add("item2");

        Car car = new Car(accessories);
        Car clone1 = (Car) car.clone();
        Car clone2 = (Car) car.clone();

        assertTrue(clone1.getAccessories() == car.getAccessories());
        assertTrue(clone2.getAccessories() == car.getAccessories());
        assertFalse(clone1 == car);
        assertFalse(clone2 == car);
        assertFalse(clone1 == clone2);
    }

    @Test
    public void testShallowCopyChangingOrigin() throws CloneNotSupportedException {
        List<String> accessories = new ArrayList<>();
        accessories.add("item1");
        accessories.add("item2");

        Car car = new Car(accessories);
        Car clone1 = (Car) car.clone();
        Car clone2 = (Car) car.clone();
        Car clone3 = (Car) car.clone();

        assertEquals(clone1.getAccessories().size(), car.getAccessories().size(), "Clone 1 and car accessories list must have the same size.");
        assertEquals(clone2.getAccessories().size(), car.getAccessories().size(), "Clone 1 and car accessories list must have the same size.");
        assertEquals(clone3.getAccessories().size(), car.getAccessories().size(), "Clone 1 and car accessories list must have the same size.");

        car.getAccessories().remove(0);

        assertEquals(1, car.getAccessories().size(), "Clone 1 and car accessories list must have the same size.");
        assertEquals(clone1.getAccessories().size(), car.getAccessories().size(), "Clone 1 and car accessories list must have the same size after accessories list be changed.");
        assertEquals(clone2.getAccessories().size(), car.getAccessories().size(), "Clone 1 and car accessories list must have the same size after accessories list be changed.");
        assertEquals(clone3.getAccessories().size(), car.getAccessories().size(), "Clone 1 and car accessories list must have the same size after accessories list be changed.");
    }

    @Test
    public void testDeepCopy(){
        List<String> accessories = new ArrayList<>();
        accessories.add("item1");
        accessories.add("item2");

        Car car = new Car(accessories);
        Car clone1 = car.deepCopy();

        assertFalse(clone1.getAccessories() == car.getAccessories());
    }

    @Test
    public void testDeepCopyChangingOrigin(){
        List<String> accessories = new ArrayList<>();
        accessories.add("item1");
        accessories.add("item2");

        Car car = new Car(accessories);
        Car clone1 = car.deepCopy();

        assertFalse(clone1.getAccessories() == car.getAccessories());

        assertEquals(clone1.getAccessories().size(), car.getAccessories().size(), "Clone 1 and car accessories list must have the same size.");

        car.getAccessories().remove(1);

        assertNotEquals(clone1.getAccessories().size(), car.getAccessories().size(), "Clone 1 and car accessories list must have the same size.");
    }
}
