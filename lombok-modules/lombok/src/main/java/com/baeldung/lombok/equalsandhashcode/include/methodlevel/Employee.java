package com.baeldung.lombok.equalsandhashcode.include.methodlevel;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Employee {
    private String name;
    private int id;
    private int age;

    @EqualsAndHashCode.Include
    public boolean hasOddId() {
        return id % 2 != 0;
    }
}

