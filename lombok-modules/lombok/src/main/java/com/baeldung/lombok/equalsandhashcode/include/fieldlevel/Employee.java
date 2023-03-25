package com.baeldung.lombok.equalsandhashcode.include.fieldlevel;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Employee {
    @EqualsAndHashCode.Include
    private String name;
    @EqualsAndHashCode.Include
    private int id;
    private int age;
}


