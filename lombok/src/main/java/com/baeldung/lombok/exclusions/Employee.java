package com.baeldung.lombok.exclusions;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Employee {

    @Setter(AccessLevel.NONE)
    private String name;

    private String workplace;

    @Getter(AccessLevel.NONE)
    private int workLength;
}
