package com.baeldung.lombok.constructor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LombokConstructorTest {

    @Test
    public void whenUsingRequiredArgsWithNonNullAnnotationAndNullableFieldIsNullThenCreateObject() {
        String name = "John";
        int age = 30;

        LombokConstructorWithRequiredArgsAndNonNull lombokConstructorWithRequiredArgsAndNonNull =
                new LombokConstructorWithRequiredArgsAndNonNull(name, age);

        assertEquals(name, lombokConstructorWithRequiredArgsAndNonNull.getName());
        assertEquals(age, lombokConstructorWithRequiredArgsAndNonNull.getAge());
        assertNull(lombokConstructorWithRequiredArgsAndNonNull.getEmail());
    }
    @Test
    public void whenRequiredFieldsAreSetThenFieldsAreUpdatedAccordingly() {
        int expectedId = 1;
        String expectedName = "Laptop";
        String expectedDescription = "A powerful laptop.";
        double expectedPrice = 1200.00;

        // When
        LombokWithRequiredArgsAndSetter lombokWithRequiredArgsAndSetter =
                new LombokWithRequiredArgsAndSetter(expectedId, expectedName);
        lombokWithRequiredArgsAndSetter.setDescription(expectedDescription);
        lombokWithRequiredArgsAndSetter.setPrice(expectedPrice);

        // Then
        assertEquals(expectedId, lombokWithRequiredArgsAndSetter.getId());
        assertEquals(expectedName,lombokWithRequiredArgsAndSetter.getName());
        assertEquals(expectedDescription, lombokWithRequiredArgsAndSetter.getDescription());
        assertEquals(expectedPrice, lombokWithRequiredArgsAndSetter.getPrice());
    }

    @Test
    public void whenUsingBuilderAnnotationAndSomeFieldIsNullThenCreateObject() {
        String name = "John";
        int age = 30;
        LombokWithBuilder lombokWithBuilder = LombokWithBuilder.builder()
                                                                .name(name)
                                                                .age(age)
                                                                .build();

        assertEquals(name, lombokWithBuilder.getName());
        assertEquals(age, lombokWithBuilder.getAge()); assertNull(lombokWithBuilder.getEmail());
    }

}