package com.baeldung.lombok.equalsandhashcode.commonissue;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class ManagerV2 {
    private String name;
    private EmployeeV2 assistantManager;
}

