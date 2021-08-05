package com.baeldung.pattern.hexagonal.domain.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The domains model is independent from any environment.
 */
public @Data class Employee {

    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private String jobTitle;

}
