package com.baeldung.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

import com.baeldung.custom.AllowedValues;

@Data
public class Employee {

    @NotEmpty(message = "Roles list cannot be empty")
    @Valid
    private List<@Pattern(regexp = "ROLE_[A-Z]+", message = "Each role must start with 'ROLE_' and contain uppercase letters only") String> roles;

    @NotEmpty(message = "Department cannot be empty")
    @AllowedValues(values = {"Management", "Software Development", "DevOps", "Architect"},
        message = "Invalid department provided")
    private List<String> department;
}
