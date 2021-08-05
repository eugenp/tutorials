package com.baeldung.pattern.hexagonal.persistence.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The entity that is mapped to the database. Might differ from the domain's model.
 */
@Entity(name="employee")
@Table(name="employee")
public @Data class EmployeeEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull
    @Size(min = 1)
    private String name;
    private String jobTitle;

}
