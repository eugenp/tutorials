package com.baeldung.swaggerenums.controller;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.swaggerenums.model.Employee;
import com.baeldung.swaggerenums.model.Role;

public class HireControllerUnitTest {

    @Test
    public void givenRoleEngineer_whenHireEmployee_thenReturnsRoleInString() {
        //Arrange
        Role testRole = Role.Engineer;
        Employee employee = new Employee();
        employee.setRole(testRole);

        //Act
        HireController hireController = new HireController();
        String response = hireController.hireEmployee(employee);

        //Assert
        Assert.assertEquals(String.format("Hired for role: %s", testRole),
                response);
    }
}
