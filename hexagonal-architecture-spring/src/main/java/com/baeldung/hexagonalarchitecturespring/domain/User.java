package com.baeldung.hexagonalarchitecturespring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {
    private UUID id;
    private String name;
    private int age;
    private boolean active;
}
