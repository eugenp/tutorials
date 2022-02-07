package com.baeldung.hexagonal.architecture.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Person {
    private Long id;
    private String name;
}
