package com.baeldung.lombok.equalsandhashcode.commonissue;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Employee {
    private String name;
    private int id;
    private int age;
    private Manager manager;
}

