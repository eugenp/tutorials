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

    @Test
    void whenUsingNoArgsAnnotationAndSomeFieldHasNoSetter_ThenCreateObject() {
        // given
        String name = "John";
        int age = 30;

        // when
        LombokConstructorWithNoArgsAndSetter lombokConstructorWithNoArgsAndSetter = new LombokConstructorWithNoArgsAndSetter();
        lombokConstructorWithNoArgsAndSetter.setAge(age);
        lombokConstructorWithNoArgsAndSetter.setName(name);

        // then
        assertEquals(name, lombokConstructorWithNoArgsAndSetter.getName());
        assertEquals(age, lombokConstructorWithNoArgsAndSetter.getAge());
        assertNull(lombokConstructorWithNoArgsAndSetter.getEmail());
    }
}