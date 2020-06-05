package com.baeldung.reflection.access.privatefields;

import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AccessPrivateMethodsUnitTest {

    private static AccessPrivateProperties accessPrivateProperties;

    @BeforeAll
    public static void beforeAll() {
        accessPrivateProperties = new AccessPrivateProperties();
    }

    @Test
    public void testForNoSuchMethodException() {
        Assertions.assertThrows(NoSuchMethodException.class, () -> accessPrivateProperties.accessPrivateMethods("greeting", String.class, "5", true));
    }

    @Test
    public void testForIllegalAccessException() {
        Assertions.assertThrows(IllegalAccessException.class, () -> accessPrivateProperties.accessPrivateMethods("greet", String.class, "5", false));
    }

    @Test
    public void testForIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> accessPrivateProperties.accessPrivateMethods("greet", String.class, 5, true));
    }

    @Test
    public void testForNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> accessPrivateProperties.accessPrivateMethods(null, String.class, "5", true));
    }

    @Test
    public void testForInvocationTargetException() {
        Assertions.assertThrows(InvocationTargetException.class, () -> accessPrivateProperties.accessPrivateMethods("greet", String.class, "John", true));
    }

}
