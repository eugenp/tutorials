package com.baeldung.lombok.equalsandhashcode;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Employee {
    private String name;
    private int id;
    private int age;
}
