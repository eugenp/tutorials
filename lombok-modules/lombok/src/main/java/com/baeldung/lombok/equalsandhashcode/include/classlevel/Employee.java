package com.baeldung.lombok.equalsandhashcode.include.classlevel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"name", "id"})
@AllArgsConstructor
public class Employee {
    private String name;
    private int id;
    private int age;
}

