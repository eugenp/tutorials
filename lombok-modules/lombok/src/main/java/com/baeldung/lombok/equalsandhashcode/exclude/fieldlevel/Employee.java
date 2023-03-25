package com.baeldung.lombok.equalsandhashcode.exclude.fieldlevel;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Employee {
    private String name;
    @EqualsAndHashCode.Exclude
    private int id;
    @EqualsAndHashCode.Exclude
    private int age;
}


