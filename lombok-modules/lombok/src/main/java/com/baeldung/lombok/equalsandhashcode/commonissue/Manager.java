package com.baeldung.lombok.equalsandhashcode.commonissue;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Manager {
    private String name;
    private Employee assistantManager;
}

