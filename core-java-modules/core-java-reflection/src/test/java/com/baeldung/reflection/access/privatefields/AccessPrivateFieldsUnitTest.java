package com.baeldung.reflection.access.privatefields;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccessPrivateFieldsUnitTest {

    @Test
    public void whenGetFields_thenSuccess() throws IllegalAccessException, IllegalArgumentException, NoSuchFieldException, NullPointerException {
        Person person = new Person();

        Field nameField = person.getClass()
            .getDeclaredField("name");
        nameField.setAccessible(true);

        String name = (String) nameField.get(person);

        Field ageField = person.getClass()
            .getDeclaredField("age");
        ageField.setAccessible(true);

        byte age = ageField.getByte(person);

        Assertions.assertEquals("John", name);
        Assertions.assertEquals(30, age);
    }

    @Test
    public void whenGetIntegerFields_thenSuccess() throws IllegalAccessException, IllegalArgumentException, NoSuchFieldException, NullPointerException {
        Person person = new Person();

        Field uidNumberField = person.getClass()
            .getDeclaredField("uidNumber");
        uidNumberField.setAccessible(true);

        short uidNumber = uidNumberField.getShort(person);

        Field pinCodeField = person.getClass()
            .getDeclaredField("pinCode");
        pinCodeField.setAccessible(true);

        int pinCode = pinCodeField.getInt(person);

        Field contactNumberField = person.getClass()
            .getDeclaredField("contactNumber");
        contactNumberField.setAccessible(true);

        long contactNumber = contactNumberField.getLong(person);

        Assertions.assertEquals(5555, uidNumber);
        Assertions.assertEquals(452002, pinCode);
        Assertions.assertEquals(123456789L, contactNumber);
    }

    @Test
    public void whenGetFloatingTypeFields_thenSuccess() throws IllegalAccessException, IllegalArgumentException, NoSuchFieldException, NullPointerException {
        Person person = new Person();

        Field heightField = person.getClass()
            .getDeclaredField("height");
        heightField.setAccessible(true);

        float height = heightField.getFloat(person);

        Field weightField = person.getClass()
            .getDeclaredField("weight");
        weightField.setAccessible(true);

        double weight = weightField.getDouble(person);

        Assertions.assertEquals(6.1242f, height);
        Assertions.assertEquals(75.2564, weight);
    }

    @Test
    public void whenGetCharacterFields_thenSuccess() throws IllegalAccessException, IllegalArgumentException, NoSuchFieldException, NullPointerException {
        Person person = new Person();

        Field genderField = person.getClass()
            .getDeclaredField("gender");
        genderField.setAccessible(true);

        char gender = genderField.getChar(person);

        Assertions.assertEquals('M', gender);
    }

    @Test
    public void whenGetBooleanFields_thenSuccess() throws IllegalAccessException, IllegalArgumentException, NoSuchFieldException, NullPointerException {
        Person person = new Person();

        Field activeField = person.getClass()
            .getDeclaredField("active");
        activeField.setAccessible(true);

        boolean active = activeField.getBoolean(person);

        Assertions.assertTrue(active);
    }

    @Test
    public void givenInt_whenGetStringField_thenIllegalArgumentException() throws IllegalAccessException, IllegalArgumentException, NoSuchFieldException, NullPointerException {
        Person person = new Person();
        Field nameField = person.getClass()
            .getDeclaredField("name");
        nameField.setAccessible(true);

        Assertions.assertThrows(IllegalArgumentException.class, () -> nameField.getInt(person));
    }

    @Test
    public void whenFieldNotSetAccessible_thenIllegalAccessException() throws IllegalAccessException, IllegalArgumentException, NoSuchFieldException, NullPointerException {
        Person person = new Person();
        Field nameField = person.getClass()
            .getDeclaredField("name");

        Assertions.assertThrows(IllegalAccessException.class, () -> nameField.get(person));
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
