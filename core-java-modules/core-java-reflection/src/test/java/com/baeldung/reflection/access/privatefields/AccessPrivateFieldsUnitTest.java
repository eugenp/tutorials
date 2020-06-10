package com.baeldung.reflection.access.privatefields;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccessPrivateFieldsUnitTest {

    @Test
    public void whenGetField_thenSuccess() throws NoSuchFieldException, NullPointerException, IllegalArgumentException, IllegalAccessException {
        Person person = new Person();
        person.setName("John");

        Field field = person.getClass()
            .getDeclaredField("name");
        field.setAccessible(true);

        String name = (String) field.get(person);

        Assertions.assertEquals("John", name);
    }

    @Test
    public void givenInt_whenSetStringField_thenIllegalArgumentException() throws NoSuchFieldException, NullPointerException, IllegalArgumentException, IllegalAccessException {
        Person person = new Person();
        Field field = person.getClass()
            .getDeclaredField("name");
        field.setAccessible(true);

        Assertions.assertThrows(IllegalArgumentException.class, () -> field.setInt(person, 25));
    }

    @Test
    public void whenFieldNotSetAccessible_thenIllegalAccessException() throws NoSuchFieldException, NullPointerException, IllegalArgumentException, IllegalAccessException {
        Person person = new Person();
        Field field = person.getClass()
            .getDeclaredField("name");

        Assertions.assertThrows(IllegalAccessException.class, () -> field.set(person, "John"));
    }

}
