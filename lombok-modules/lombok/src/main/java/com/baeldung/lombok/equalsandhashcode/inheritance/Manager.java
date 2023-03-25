package com.baeldung.lombok.equalsandhashcode.inheritance;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class Manager extends Employee {
    private String departmentName;
    private int uid;
}
