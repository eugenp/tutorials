package com.baeldung.reflection.access.privatefields;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AccessPrivateFieldsUnitTest {

    private static AccessPrivateProperties accessPrivateProperties;

    @BeforeAll
    public static void beforeAll() {
        accessPrivateProperties = new AccessPrivateProperties();
    }

    @Test
    public void testForNoSuchFieldException() {
        Assertions.assertThrows(NoSuchFieldException.class, () -> accessPrivateProperties.accessPrivateFields("firstName", "John", true));
    }

    @Test
    public void testForIllegalAccessException() {
        Assertions.assertThrows(IllegalAccessException.class, () -> accessPrivateProperties.accessPrivateFields("name", "John", false));
    }

    @Test
    public void testForIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> accessPrivateProperties.accessPrivateFields("name", 25, true));
    }

    @Test
    public void testForNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> accessPrivateProperties.accessPrivateFields(null, "John", true));
    }

}
