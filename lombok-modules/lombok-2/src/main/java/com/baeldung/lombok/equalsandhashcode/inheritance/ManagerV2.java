package com.baeldung.lombok.equalsandhashcode.inheritance;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ManagerV2 extends Employee {
    private String departmentName;
    private int uid;

    public ManagerV2(String departmentName, int uid, String name, int id, int age) {
        super(name, id, age);
        this.departmentName = departmentName;
        this.uid = uid;
    }
}
