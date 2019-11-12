package com.baeldung.hexagonal.architecture.holiday.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class Employee {

    private UUID id;
    private String name;
    private ContractType contractType;

}
