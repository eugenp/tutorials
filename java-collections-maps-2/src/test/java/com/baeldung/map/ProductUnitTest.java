package com.baeldung.map;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ProductUnitTest {


    @Test
    public void getExistingValue() {
        HashMap<String, Product> productsByName = new HashMap<>();

        Product eBike = new Product("E-Bike", "A bike with a battery");
        Product roadBike = new Product("Road bike", "A bike for competition");

        productsByName.put(eBike.getName(), eBike);
        productsByName.put(roadBike.getName(), roadBike);

        Product nextPurchase = productsByName.get("E-Bike");

        assertEquals("A bike with a battery", nextPurchase.getDescription());
    }

    @Test
    public void getNonExistingValue() {
        HashMap<String, Product> productsByName = new HashMap<>();

        Product eBike = new Product("E-Bike", "A bike with a battery");
        Product roadBike = new Product("Road bike", "A bike for competition");

        productsByName.put(eBike.getName(), eBike);
        productsByName.put(roadBike.getName(), roadBike);

        Product nextPurchase = productsByName.get("Car");

        assertNull(nextPurchase);
    }

    @Test
    public void getExistingValueAfterSameKeyInsertedTwice() {
        HashMap<String, Product> productsByName = new HashMap<>();

        Product eBike = new Product("E-Bike", "A bike with a battery");
        Product roadBike = new Product("Road bike", "A bike for competition");
        Product newEBike = new Product("E-Bike", "A bike with a better battery");

        productsByName.put(eBike.getName(), eBike);
        productsByName.put(roadBike.getName(), roadBike);
        productsByName.put(newEBike.getName(), newEBike);

        Product nextPurchase = productsByName.get("E-Bike");

        assertEquals("A bike with a better battery", nextPurchase.getDescription());
    }

    @Test
    public void getExistingValueWithNullKey() {
        HashMap<String, Product> productsByName = new HashMap<>();

        Product defaultProduct = new Product("Chocolate", "At least buy chocolate");

        productsByName.put(null, defaultProduct);
        productsByName.put(defaultProduct.getName(), defaultProduct);

        Product nextPurchase = productsByName.get(null);
        assertEquals("At least buy chocolate", nextPurchase.getDescription());

        nextPurchase = productsByName.get("Chocolate");
        assertEquals("At least buy chocolate", nextPurchase.getDescription());
    }

    @Test
    public void insertSameObjectWithDifferentKey() {
        HashMap<String, Product> productsByName = new HashMap<>();

        Product defaultProduct = new Product("Chocolate", "At least buy chocolate");

        productsByName.put(null, defaultProduct);
        productsByName.put(defaultProduct.getName(), defaultProduct);

        assertSame(productsByName.get(null), productsByName.get("Chocolate"));
    }

    @Test
    public void checkIfKeyExists() {
        HashMap<String, Product> productsByName = new HashMap<>();

        Product eBike = new Product("E-Bike", "A bike with a battery");

        productsByName.put(eBike.getName(), eBike);

        assertTrue(productsByName.containsKey("E-Bike"));
    }

    @Test
    public void checkIfValueExists() {
        HashMap<String, Product> productsByName = new HashMap<>();

        Product eBike = new Product("E-Bike", "A bike with a battery");

        productsByName.put(eBike.getName(), eBike);

        assertTrue(productsByName.containsValue(eBike));
    }

    @Test
    public void removeExistingKey() {
        HashMap<String, Product> productsByName = new HashMap<>();

        Product eBike = new Product("E-Bike", "A bike with a battery");
        Product roadBike = new Product("Road bike", "A bike for competition");

        productsByName.put(eBike.getName(), eBike);
        productsByName.put(roadBike.getName(), roadBike);

        productsByName.remove("E-Bike");

        assertNull(productsByName.get("E-Bike"));
    }

}
