package com.baeldung.constructorversussettermethod;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConstructorsVersusSetterMethodsUnitTest {
    @Test
    public void testSetUsername() {
        User user = new User();
        user.setUsername("john_doe");
        assertEquals("john_doe", user.getUsername());
    }

    @Test
    public void testSetPassword() {
        User user = new User();
        user.setPassword("strongPassword123");
        assertEquals("strongPassword123", user.getPassword());
    }

    @Test
    public void testProductConstructor() {
        Product product = new Product("Smartphone", 599.99, "Electronics");
        assertEquals("Smartphone", product.getName());
        assertEquals(599.99, product.getPrice(), 0.001);
        assertEquals("Electronics", product.getCategory());
    }
}

