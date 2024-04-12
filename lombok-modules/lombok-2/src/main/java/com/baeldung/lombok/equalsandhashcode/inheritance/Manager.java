package com.baeldung.lombok.equalsandhashcode.inheritance;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Manager extends Employee {
    private String departmentName;
    private int uid;

    public Manager(String departmentName, int uid, String name, int id, int age) {
        super(name, id, age);
        this.departmentName = departmentName;
        this.uid = uid;
    }
}
