package com.baeldung.javaxval.javabeanconstraints.application;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

import com.baeldung.javaxval.javabeanconstraints.entities.UserNotBlank;

public class Application {

    public static void main(String[] args) {
        Validator validator = Validation.buildDefaultValidatorFactory()
            .getValidator();
        UserNotBlank user = new UserNotBlank(" ");
        validator.validate(user)
            .stream()
            .forEach(violation -> System.out.println(violation.getMessage()));
    }
}
