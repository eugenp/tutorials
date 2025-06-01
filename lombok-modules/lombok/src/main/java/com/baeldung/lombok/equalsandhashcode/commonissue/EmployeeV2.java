package com.baeldung.lombok.equalsandhashcode.commonissue;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(exclude = "manager")
@AllArgsConstructor
public class EmployeeV2 {
    private String name;
    private int id;
    private int age;
    private ManagerV2 manager;
}
