package com.baeldung.lombok.constructor;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LombokConstructorWithAllArgsAndFinal {
    private final String firstName = "John";
    private final int age = 30;
    private String email;
}