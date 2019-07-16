package com.baeldung.freebuilder.builder.classic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class EmployeeBuilderUnitTest {

    private static final String CITY = "New York";

    @Test
    public void whenBuildEmployeeWithAddress_thenReturnEmployeeWithValidAddress() {

        // when
        Employee.Builder emplBuilder = new Employee.Builder();

        Employee employee = emplBuilder
          .setName("baeldung")
          .setAge(12)
          .setDepartment("Builder Pattern")
          .setDesignation("Author")
          .setEmail("abc@xyz.com")
          .setPermanent(true)
          .setSupervisorName("Admin")
          .setPhoneNumber(4445566)
          .build();

        //then
        Assertions.assertTrue(employee.getAddress().getCity().equalsIgnoreCase(CITY));
    }

}