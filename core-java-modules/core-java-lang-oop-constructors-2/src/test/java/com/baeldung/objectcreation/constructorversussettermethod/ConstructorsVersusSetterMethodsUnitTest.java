package com.baeldung.objectcreation.constructorversussettermethod;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConstructorsVersusSetterMethodsUnitTest {
    @Test
    public void givenNewUser_whenSettingUsername_thenUsernameIsSet() {
        User user = new User();
        user.setUsername("john_doe");
        assertEquals("john_doe", user.getUsername());
    }

    @Test
    public void givenNewUser_whenSettingPassword_thenPasswordIsSet() {
        User user = new User();
        user.setPassword("strongPassword123");
        assertEquals("strongPassword123", user.getPassword());
    }

    @Test
    public void givenProductDetails_whenCreatingProductWithConstructor_thenProductHasCorrectAttributes() {
        Product product = new Product("Smartphone", 599.99, "Electronics");
        assertEquals("Smartphone", product.getName());
        assertEquals(599.99, product.getPrice(), 0.001);
        assertEquals("Electronics", product.getCategory());
    }
}

