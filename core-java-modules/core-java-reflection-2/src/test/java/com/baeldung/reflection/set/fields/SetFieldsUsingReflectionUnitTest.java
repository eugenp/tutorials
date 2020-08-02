package com.baeldung.reflection.set.fields;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.baeldung.reflection.access.privatefields.Person;

public class SetFieldsUsingReflectionUnitTest {

    @Test
    public void whenSetIntegerFields_thenSuccess() throws Exception {
        Person person = new Person();

        Field ageField = person.getClass()
            .getDeclaredField("age");
        ageField.setAccessible(true);

        byte age = 26;
        ageField.setByte(person, age);
        Assertions.assertEquals(26, person.getAge());

        Field uidNumberField = person.getClass()
            .getDeclaredField("uidNumber");
        uidNumberField.setAccessible(true);

        short uidNumber = 5555;
        uidNumberField.setShort(person, uidNumber);
        Assertions.assertEquals(5555, person.getUidNumber());

        Field pinCodeField = person.getClass()
            .getDeclaredField("pinCode");
        pinCodeField.setAccessible(true);

        int pinCode = 411057;
        pinCodeField.setInt(person, pinCode);
        Assertions.assertEquals(411057, person.getPinCode());

        Field contactNumberField = person.getClass()
            .getDeclaredField("contactNumber");
        contactNumberField.setAccessible(true);

        long contactNumber = 123456789L;
        contactNumberField.setLong(person, contactNumber);
        Assertions.assertEquals(123456789L, person.getContactNumber());

    }

    @Test
    public void whenDoUnboxing_thenSuccess() throws Exception {
        Person person = new Person();

        Field pinCodeField = person.getClass()
            .getDeclaredField("pinCode");
        pinCodeField.setAccessible(true);

        Integer pinCode = 411057;
        pinCodeField.setInt(person, pinCode);
        Assertions.assertEquals(411057, person.getPinCode());
    }

    @Test
    public void whenDoNarrowing_thenSuccess() throws Exception {
        Person person = new Person();

        Field pinCodeField = person.getClass()
            .getDeclaredField("pinCode");
        pinCodeField.setAccessible(true);

        short pinCode = 4110;
        pinCodeField.setInt(person, pinCode);
        Assertions.assertEquals(4110, person.getPinCode());
    }

    @Test
    public void whenSetFloatingTypeFields_thenSuccess() throws Exception {
        Person person = new Person();

        Field heightField = person.getClass()
            .getDeclaredField("height");
        heightField.setAccessible(true);

        float height = 6.1242f;
        heightField.setFloat(person, height);
        Assertions.assertEquals(6.1242f, person.getHeight());

        Field weightField = person.getClass()
            .getDeclaredField("weight");
        weightField.setAccessible(true);

        double weight = 75.2564;
        weightField.setDouble(person, weight);
        Assertions.assertEquals(75.2564, person.getWeight());
    }

    @Test
    public void whenSetCharacterFields_thenSuccess() throws Exception {
        Person person = new Person();

        Field genderField = person.getClass()
            .getDeclaredField("gender");
        genderField.setAccessible(true);

        char gender = 'M';
        genderField.setChar(person, gender);
        Assertions.assertEquals('M', person.getGender());
    }

    @Test
    public void whenSetBooleanFields_thenSuccess() throws Exception {
        Person person = new Person();

        Field activeField = person.getClass()
            .getDeclaredField("active");
        activeField.setAccessible(true);

        boolean active = true;
        activeField.setBoolean(person, active);
        Assertions.assertTrue(person.isActive());
    }

    @Test
    public void whenSetObjectFields_thenSuccess() throws Exception {
        Person person = new Person();

        Field nameField = person.getClass()
            .getDeclaredField("name");
        nameField.setAccessible(true);

        String name = "Umang Budhwar";
        nameField.set(person, name);
        Assertions.assertEquals("Umang Budhwar", person.getName());
    }

    @Test
    public void givenInt_whenSetStringField_thenIllegalArgumentException() throws Exception {
        Person person = new Person();
        Field nameField = person.getClass()
            .getDeclaredField("name");
        nameField.setAccessible(true);

        Assertions.assertThrows(IllegalArgumentException.class, () -> nameField.setInt(person, 26));
    }

    @Test
    public void givenInt_whenSetLongField_thenIllegalArgumentException() throws Exception {
        Person person = new Person();

        Field pinCodeField = person.getClass()
            .getDeclaredField("pinCode");
        pinCodeField.setAccessible(true);

        long pinCode = 411057L;

        Assertions.assertThrows(IllegalArgumentException.class, () -> pinCodeField.setLong(person, pinCode));
    }

    @Test
    public void whenFieldNotSetAccessible_thenIllegalAccessException() throws Exception {
        Person person = new Person();
        Field nameField = person.getClass()
            .getDeclaredField("name");

        Assertions.assertThrows(IllegalAccessException.class, () -> nameField.set(person, "Umang Budhwar"));
    }

    @Test
    public void whenAccessingWrongProperty_thenNoSuchFieldException() throws Exception {
        Person person = new Person();

        Assertions.assertThrows(NoSuchFieldException.class, () -> person.getClass()
            .getDeclaredField("firstName"));
    }

    @Test
    public void whenAccessingNullProperty_thenNullPointerException() throws Exception {
        Person person = new Person();

        Assertions.assertThrows(NullPointerException.class, () -> person.getClass()
            .getDeclaredField(null));
    }

}
