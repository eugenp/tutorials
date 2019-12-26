package com.baeldung.lombok.builder.inheritance.superbuilder;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class Child extends Parent {
    private final String childName;
    private final int childAge;
}
