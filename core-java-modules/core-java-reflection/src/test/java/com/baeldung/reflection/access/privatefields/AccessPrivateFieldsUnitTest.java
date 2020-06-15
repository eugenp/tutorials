package com.baeldung.reflection.access.privatefields;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccessPrivateFieldsUnitTest {

    @Test
    public void whenGetFields_thenSuccess() throws IllegalAccessException, IllegalArgumentException, NoSuchFieldException, NullPointerException {
        Person person = new Person();

        Field fieldName = person.getClass()
            .getDeclaredField("name");
        fieldName.setAccessible(true);

        String name = (String) fieldName.get(person);

        Field fieldAge = person.getClass()
            .getDeclaredField("age");
        fieldAge.setAccessible(true);

        int age = fieldAge.getInt(person);

        Assertions.assertEquals("John", name);
        Assertions.assertEquals(30, age);
    }

    @Test
    public void givenInt_whenSetStringField_thenIllegalArgumentException() throws IllegalAccessException, IllegalArgumentException, NoSuchFieldException, NullPointerException {
        Person person = new Person();
        Field fieldName = person.getClass()
            .getDeclaredField("name");
        fieldName.setAccessible(true);

        Assertions.assertThrows(IllegalArgumentException.class, () -> fieldName.setInt(person, 25));
    }

    @Test
    public void whenFieldNotSetAccessible_thenIllegalAccessException() throws IllegalAccessException, IllegalArgumentException, NoSuchFieldException, NullPointerException {
        Person person = new Person();
        Field fieldName = person.getClass()
            .getDeclaredField("name");

        Assertions.assertThrows(IllegalAccessException.class, () -> fieldName.get(person));
    }

    @Test
    public void whenAccessingWrongProperty_thenNoSuchFieldException() throws NoSuchFieldException, NullPointerException {
        Person person = new Person();

        Assertions.assertThrows(NoSuchFieldException.class, () -> person.getClass()
            .getDeclaredField("genders"));
    }

    @Test
    public void whenAccessingNullProperty_thenNullPointerException() throws NoSuchFieldException, NullPointerException {
        Person person = new Person();

        Assertions.assertThrows(NullPointerException.class, () -> person.getClass()
            .getDeclaredField(null));
    }

}
