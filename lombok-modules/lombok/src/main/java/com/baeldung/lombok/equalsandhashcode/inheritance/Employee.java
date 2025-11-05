package com.baeldung.lombok.equalsandhashcode.inheritance;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class Employee {
    private String name;
    private int id;
    private int age;
}
