package com.baeldung.hexagonal.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Test EmployeeServices for equality
 */
class EmployeeServicesTest {

  @Test
  void testEquals() {
    EmployeeService empService = new EmployeeService();
    EmployeeDetails details = new EmployeeDetails("firstname", "lastname");
    EmployeeId employeeId = new EmployeeId();
    Employee employee = new Employee(employeeId, details);
    Optional<EmployeeId> oemployeeId= empService.insertEmployee(employee);
    assertEquals(oemployeeId.get().getId(), 2);
    assertEquals(empService.retrieveEmployee(oemployeeId.get()).get().getEmployeeDetails().getFirstName(), "firstname");
  }
}
