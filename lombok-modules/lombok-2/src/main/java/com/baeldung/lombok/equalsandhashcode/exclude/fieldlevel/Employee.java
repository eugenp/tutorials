package com.baeldung.lombok.equalsandhashcode.exclude.fieldlevel;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Employee {
    private String name;
    @EqualsAndHashCode.Exclude
    private int id;
    @EqualsAndHashCode.Exclude
    private int age;
}


