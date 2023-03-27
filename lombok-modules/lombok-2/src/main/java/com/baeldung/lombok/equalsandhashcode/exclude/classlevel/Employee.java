package com.baeldung.lombok.equalsandhashcode.exclude.classlevel;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(exclude = {"id", "age"})
@AllArgsConstructor
public class Employee {
    private String name;
    private int id;
    private int age;
}

