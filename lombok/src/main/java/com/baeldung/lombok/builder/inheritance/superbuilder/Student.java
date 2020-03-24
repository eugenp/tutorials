package com.baeldung.lombok.builder.inheritance.superbuilder;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class Student extends Child {
    private final String schoolName;
}
