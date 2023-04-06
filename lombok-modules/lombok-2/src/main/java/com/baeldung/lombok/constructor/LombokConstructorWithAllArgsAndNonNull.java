package com.baeldung.lombok.constructor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public class LombokConstructorWithAllArgsAndNonNull {
    @NonNull
    private String firstName;

    @NonNull
    private int age;

    private String email;
}