package com.baeldung.lombok.constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class LombokConstructorUnitTest {

    @Test
    void whenUsingRequiredArgsWithNonNullAnnotationAndNullableFieldIsNull_ThenCreateObject() {
        // given
        String name = "John";
        int age = 30;

        // when
        LombokConstructorWithRequiredArgsAndNonNull lombokConstructorWithRequiredArgsAndNonNull = new LombokConstructorWithRequiredArgsAndNonNull(name, age);

        // then
        assertEquals(name, lombokConstructorWithRequiredArgsAndNonNull.getName());
        assertEquals(age, lombokConstructorWithRequiredArgsAndNonNull.getAge());
    }

    @Test
    void whenUsingRequiredArgsWithNonNullAnnotationAndNonNullFieldIsNull_ThenThrowError() {
        // given
        String name = "John";
        Integer age = null;

        // when
        assertThrows(NullPointerException.class, () -> new LombokConstructorWithRequiredArgsAndNonNull(name, age));
    }

    @Test
    void whenUsingRequiredArgsWithFinalAnnotationAndInitializedFinalFieldIsNotPassed_ThenCreateObject() {
        // given
        String name = "John";
        int age = 30;

        // when
        LombokConstructorWithRequiredArgsAndFinal lombokConstructorWithRequiredArgsAndFinal = new LombokConstructorWithRequiredArgsAndFinal(name, age);

        // then
        assertEquals(name, lombokConstructorWithRequiredArgsAndFinal.getName());
        assertEquals(age, lombokConstructorWithRequiredArgsAndFinal.getAge());
    }

    @Test
    void whenUsingRequiredArgsWithFinalAnnotationAndNonInitializedFinalFieldIsNotPassed_ThenThrowError() {
        // given
        String name = "John";
        Integer age = null;

        // when
        assertThrows(NullPointerException.class, () -> new LombokConstructorWithRequiredArgsAndFinal(name, age));
    }

    @Test
    void whenUsingAllArgsWithNonNullAnnotationAndNullableFieldIsNull_ThenCreateObject() {
        // given
        String name = "John";
        int age = 30;

        // when
        LombokConstructorWithAllArgsAndNonNull lombokConstructorWithAllArgsAndNonNull = new LombokConstructorWithAllArgsAndNonNull(name, age, null);

        // then
        assertEquals(name, lombokConstructorWithAllArgsAndNonNull.getFirstName());
        assertEquals(age, lombokConstructorWithAllArgsAndNonNull.getAge());
        assertNull(lombokConstructorWithAllArgsAndNonNull.getEmail());
    }

    @Test
    void whenUsingAllArgsWithNonNullAnnotationAndNonNullFieldIsNull_ThenThrowError() {
        // given
        String name = "John";
        Integer age = null;
        String email = "hello@dummyEmail.com";

        // when
        assertThrows(NullPointerException.class, () -> new LombokConstructorWithAllArgsAndNonNull(name, age, email));
    }

    @Test
    void whenUsingAllArgsWithFinalAnnotationAndFinalFieldIsNotPassedInConstructor_ThenCreateObject() {
        String email = "dummy_email@dummy.com";
        String firstName = "John";
        int age = 30;

        LombokConstructorWithAllArgsAndFinal lombokConstructorWithAllArgsAndFinal = new LombokConstructorWithAllArgsAndFinal(email);

        assertEquals(firstName, lombokConstructorWithAllArgsAndFinal.getFirstName());
        assertEquals(age, lombokConstructorWithAllArgsAndFinal.getAge());
        assertEquals(email, lombokConstructorWithAllArgsAndFinal.getEmail());
    }

    @Test
    void whenUsingAllArgsWithFinalAnnotationAndNonFinalFieldIsNullInConstructor_ThenCreateObject() {
        // given
        String firstName = "John";
        int age = 30;

        // when
        LombokConstructorWithAllArgsAndFinal lombokConstructorWithAllArgsAndFinal = new LombokConstructorWithAllArgsAndFinal(null);

        // then
        assertEquals(firstName, lombokConstructorWithAllArgsAndFinal.getFirstName());
        assertEquals(age, lombokConstructorWithAllArgsAndFinal.getAge());
        assertNull(lombokConstructorWithAllArgsAndFinal.getEmail());
    }

    @Test
    void whenUsingBuilderAnnotationAndSomeFieldIsNull_ThenCreateObject() {
        // given
        String name = "John";
        int age = 30;

        // when
        LombokWithBuilder lombokWithBuilder = LombokWithBuilder.builder().name(name).age(age).build();

        // then
        assertEquals(name, lombokWithBuilder.getName());
        assertEquals(age, lombokWithBuilder.getAge());
        assertNull(lombokWithBuilder.getEmail());
    }
}