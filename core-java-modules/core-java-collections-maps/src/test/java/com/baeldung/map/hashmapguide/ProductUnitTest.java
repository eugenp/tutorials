package com.baeldung.map.hashmapguide;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.Test;

class ProductUnitTest {

    @Test
    public void whenUsingObjectAsHashMapGenericParameter_ShouldRequireCast() throws Exception {

        try {
            HashMap<String,Object> objectMap = new HashMap<String,Object>();

            Product eBike = new Product("E-Bike", "A bike with a battery");
            Product roadBike = new Product("Road bike", "A bike for competition");

            objectMap.put("E-Bike", eBike);
            objectMap.put("Road bike", roadBike);

            HashMap<String,Product> productMap = new HashMap<String,Product>();

            for (Map.Entry<String, Object> entry: objectMap.entrySet()) {
                if (entry.getValue() instanceof Product) {
                    productMap.put(entry.getKey(), (Product) entry.getValue());
                }
            }

            Product product = productMap.get("E-Bike");
            String actualDescription = product.getDescription();
            String expectedDescription = new String("A bike with a battery");

            assertTrue(actualDescription.equals(expectedDescription));

        } catch(ClassCastException e) {
              System.out.println(e.getMessage());
          }
    }

    @Test
    public void whenUsingProperHashMapGenericParameters_ShouldNotRequireCast() throws Exception {
        HashMap<String,Product> productMap = new HashMap<String,Product>();

        Product eBike = new Product("E-Bike", "A bike with a battery");
        Product roadBike = new Product("Road bike", "A bike for competition");

        productMap.put(eBike.getName(), eBike);
        productMap.put(roadBike.getName(), roadBike);

        Product product = productMap.get("E-Bike");
        String actualDescription = product.getDescription();
        String expectedDescription = new String("A bike with a battery");

        assertTrue(actualDescription.equals(expectedDescription));

        HashMap<String,Product> objectMap = productMap;
        assertSame(objectMap,productMap);
    }

    
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

    @Test
    public void givenMutableKeyWhenKeyChangeThenValueNotFound() {
        // Given
        MutableKey key = new MutableKey("initial");

        Map<MutableKey, String> items = new HashMap<>();
        items.put(key, "success");

        // When
        key.setName("changed");

        // Then
        assertNull(items.get(key));
    }

    static class MutableKey {
        private String name;

        public MutableKey(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            MutableKey that = (MutableKey) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

}
