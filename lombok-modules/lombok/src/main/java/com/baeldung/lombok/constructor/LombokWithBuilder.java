package com.baeldung.lombok.constructor;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LombokWithBuilder {
    private String name;

    private int age;

    private String email;
}