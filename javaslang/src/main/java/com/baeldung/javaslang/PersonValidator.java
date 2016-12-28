package com.baeldung.javaslang;

import javaslang.collection.List;
import javaslang.control.Validation;

class PersonValidator {
    String NAME_ERR = "Invalid characters in name: ";
    String AGE_ERR = "Age must be at least 0";

    public Validation<List<String>, Person> validatePerson(String name, int age) {
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
