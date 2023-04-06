package com.baeldung.lombok.constructor;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LombokConstructorWithRequiredArgsAndNonNull {
    @NonNull
    private String name;

    @NonNull
    private int age;

    private String email;
}