package com.baeldung.pattern.hexagonal.boundary.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The HTTP representation of the employee. It might differ from the domain's model.
 */
public @Data class EmployeeDto {

    @EqualsAndHashCode.Include
    private Long id;
    @NotNull
    @Size(min = 1)
    private String name;
    @JsonProperty("job_title")
    private String jobTitle;

}
