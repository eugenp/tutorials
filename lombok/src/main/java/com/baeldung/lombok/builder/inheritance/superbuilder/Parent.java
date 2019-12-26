package com.baeldung.lombok.builder.inheritance.superbuilder;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class Parent {
    private final String parentName; 
    private final int parentAge; 
}
