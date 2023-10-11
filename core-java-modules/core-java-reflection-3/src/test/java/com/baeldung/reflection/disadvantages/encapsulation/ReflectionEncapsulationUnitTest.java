package com.baeldung.reflection.disadvantages.encapsulation;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReflectionEncapsulationUnitTest {

    @Test
    public void givenPrivateField_whenUsingReflection_thenIsAccessible() throws IllegalAccessException, NoSuchFieldException {
        MyClass myClassInstance = new MyClass();

        Field privateField = MyClass.class.getDeclaredField("veryPrivateField");
        privateField.setAccessible(true);

        String accessedField = privateField.get(myClassInstance).toString();
        assertEquals(accessedField, "Secret Information");
    }
}
