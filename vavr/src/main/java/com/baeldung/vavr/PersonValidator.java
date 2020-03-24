package com.baeldung.vavr;

import io.vavr.collection.Seq;
import io.vavr.control.Validation;

class PersonValidator {
    private static final String NAME_ERR = "Invalid characters in name: ";
    private static final String AGE_ERR = "Age must be at least 0";

    Validation<Seq<String>, Person> validatePerson(String name, int age) {
        return Validation.combine(validateName(name), validateAge(age)).ap(Person::new);
    }

    private Validation<String, String> validateName(String name) {
        String invalidChars = name.replaceAll("[a-zA-Z ]", "");
        return invalidChars.isEmpty() ? Validation.valid(name) : Validation.invalid(NAME_ERR + invalidChars);
    }

    private Validation<String, Integer> validateAge(int age) {
        return age < 0 ? Validation.invalid(AGE_ERR) : Validation.valid(age);
    }
}
