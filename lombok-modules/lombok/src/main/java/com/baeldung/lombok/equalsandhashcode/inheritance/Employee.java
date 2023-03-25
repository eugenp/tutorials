package com.baeldung.lombok.equalsandhashcode.inheritance;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Employee {
    private String name;
    private int id;
    private int age;
}
