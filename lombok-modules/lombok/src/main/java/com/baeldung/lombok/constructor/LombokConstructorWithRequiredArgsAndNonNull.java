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
    private Integer age;

    private String email;
}
