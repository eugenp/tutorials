package com.baeldung.lombok.equalsandhashcode.exclude.classlevel;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(exclude = {"id", "age"})
public class Employee {
    private String name;
    private int id;
    private int age;
}

