package com.baeldung.lombok.constructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class LombokConstructorWithNoArgsAndSetter {
    @Setter
    private String name;

    @Setter
    private int age;

    private String email;
}
