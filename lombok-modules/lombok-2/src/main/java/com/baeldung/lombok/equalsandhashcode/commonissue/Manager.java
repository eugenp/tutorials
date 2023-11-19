package com.baeldung.lombok.equalsandhashcode.commonissue;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class Manager {
    private String name;
    private Employee assistantManager;
}

