package com.baeldung.lombok.equalsandhashcode.commonissue;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private String name;
    private int id;
    private int age;
    private Manager manager;
}

