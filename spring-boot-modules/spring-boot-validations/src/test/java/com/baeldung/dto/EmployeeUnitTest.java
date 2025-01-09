package com.baeldung.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeeUnitTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void givenRolesAndDept_whenValidRoles_thenNoValidationErrors() {
        Employee request = new Employee();
        List<String> roles = new ArrayList<>();
        List<String> dept = new ArrayList<>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_USER");
        dept.add("Architect");
        request.setRoles(roles);
        request.setDepartment(dept);

        boolean isValid = validator.validate(request)
            .isEmpty();
        assertTrue(isValid);
    }

    @Test
    void givenRoles_whenInvalidRoleFormat_thenValidationFails() {
        Employee request = new Employee();
        List<String> roles = new ArrayList<>();
        roles.add("admin");
        roles.add("ROLE_USER");
        request.setRoles(roles);

        boolean isValid = validator.validate(request)
            .isEmpty();
        assertFalse(isValid);
    }

    @Test
    void givenRoles_whenEmptyRoles_thenValidationFails() {
        Employee request = new Employee();
        request.setRoles(new ArrayList<>());

        boolean isValid = validator.validate(request)
            .isEmpty();
        assertFalse(isValid);
    }

    @Test
    void givenDeptAndRole_whenValidDept_thenNoValidationErrors() {
        Employee request = new Employee();
        List<String> department = new ArrayList<>();
        List<String> roles = new ArrayList<>();
        department.add("Software Development");
        department.add("Architect");
        roles.add("ROLE_ADMIN");
        request.setRoles(roles);
        request.setDepartment(department);

        Set violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "Validation should pass for valid dept");
    }

    @Test
    void givenDeptAndRole_whenInValidDept_thenValidationErrors() {
        Employee request = new Employee();
        List<String> department = new ArrayList<>();
        List<String> roles = new ArrayList<>();
        department.add("SDE");
        roles.add("ROLE_ADMIN");
        request.setRoles(roles);
        request.setDepartment(department);

        Set violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "Validation should pass for valid dept");
    }
}
