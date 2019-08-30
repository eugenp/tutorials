package com.baeldung.lombok.builder.inheritance.buildermethodname;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Parent {    
    private final String parentName;
    private final int parentAge;
}