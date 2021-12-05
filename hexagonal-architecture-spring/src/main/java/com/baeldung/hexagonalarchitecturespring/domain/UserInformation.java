package com.baeldung.hexagonalarchitecturespring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserInformation {
    private String name;
    private int age;
}
