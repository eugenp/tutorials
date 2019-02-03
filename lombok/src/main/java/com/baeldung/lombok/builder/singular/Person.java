package com.baeldung.lombok.builder.singular;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Getter
@Builder
public class Person {

    private final String givenName;
    private final String additionalName;
    private final String familyName;

    private final List<String> tags;
    @Singular private final List<String> interests;


}
