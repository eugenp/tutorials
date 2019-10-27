package com.baeldung.freebuilder;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeeBuilderUnitTest {

    private static final int PIN_CODE = 223344;
    public static final String CITY_NAME = "New York";
    public static final int INPUT_SALARY_EUROS = 10000;
    public static final double EUROS_TO_USD_RATIO = 0.6;

    @Test
    public void whenBuildEmployeeWithAddress_thenReturnEmployeeWithValidAddress() {

        // when
        Address.Builder addressBuilder = new Address.Builder();
        Address address = addressBuilder.setCity(CITY_NAME).build();

        Employee.Builder builder = new Employee.Builder();

        Employee employee = builder.setName("baeldung")
          .setAge(10)
          .setDesignation("author")
          .setEmail("abc@xyz.com")
          .setSupervisorName("Admin")
          .setPhoneNumber(4445566)
          .setPermanent(true)
          .setRole("developer")
          .setAddress(address)
          .build();

        // then
        assertTrue(employee.getAddress().getCity().equalsIgnoreCase(CITY_NAME));

    }

    @Test
    public void whenMapSalary_thenReturnEmployeeWithSalaryInUSD() {

        // when
        Address.Builder addressBuilder = new Address.Builder();
        Address address = addressBuilder.setCity(CITY_NAME).setPinCode(PIN_CODE).build();

        long salaryInEuros = INPUT_SALARY_EUROS;
        Employee.Builder builder = new Employee.Builder();

        Employee employee = builder
          .setName("baeldung")
          .setAge(10)
          .setDesignation("author")
          .setEmail("abc@xyz.com")
          .setSupervisorName("Admin")
          .setPhoneNumber(4445566)
          .setPermanent(true)
          .setRole("developer")
          .setAddress(address)
          .mapSalaryInUSD(sal -> salaryInEuros * EUROS_TO_USD_RATIO)
          .build();

        // then
        assertTrue(employee.getAddress().getPinCode().get() == PIN_CODE);

    }

    @Test
    public void whenOptionalFields_thenReturnEmployeeWithEmptyValues() {

        // when
        Address.Builder addressBuilder = new Address.Builder();
        Address address = addressBuilder.setCity(CITY_NAME).build();

        Employee.Builder builder = new Employee.Builder();

        Employee employee = builder.setName("baeldung")
          .setAge(10)
          .setDesignation("author")
          .setEmail("abc@xyz.com")
          .setSupervisorName("Admin")
          .setPhoneNumber(4445566)
          .setPermanent(true)
          .setRole("developer")
          .setAddress(address)
          .build();

        // then
        assertTrue(employee.getPermanent().isPresent());
        assertTrue(employee.getPermanent().get());
        assertFalse(employee.getDateOfJoining().isPresent());

    }

    @Test
    public void whenNullableFields_thenReturnEmployeeWithNullValueForField() {

        // when
        Address.Builder addressBuilder = new Address.Builder();
        Address address = addressBuilder.setCity(CITY_NAME).build();

        Employee.Builder builder = new Employee.Builder();

        Employee employee = builder.setName("baeldung")
          .setAge(10)
          .setDesignation("author")
          .setEmail("abc@xyz.com")
          .setSupervisorName("Admin")
          .setPhoneNumber(4445566)
          .setNullablePermanent(null)
          .setDateOfJoining(Optional.empty())
          .setRole("developer")
          .setAddress(address)
          .build();

        // then
        assertNull(employee.getCurrentProject());

    }

    @Test
    public void whenCollectionFields_thenReturnEmployeeWithValues() {

        // when
        Address.Builder addressBuilder = new Address.Builder();
        Address address = addressBuilder.setCity(CITY_NAME).build();

        Employee.Builder builder = new Employee.Builder();

        Employee employee = builder.setName("baeldung")
          .setAge(10)
          .setDesignation("author")
          .setEmail("abc@xyz.com")
          .setSupervisorName("Admin")
          .setPhoneNumber(4445566)
          .setNullablePermanent(null)
          .setDateOfJoining(Optional.empty())
          .setRole("developer")
          .addAccessTokens(1221819L)
          .addAccessTokens(1223441L, 134567L)
          .setAddress(address)
          .build();

        // then
        assertTrue(employee.getAccessTokens().size() == 3);

    }

    @Test
    public void whenMapFields_thenReturnEmployeeWithValues() {

        // when
        Address.Builder addressBuilder = new Address.Builder();
        Address address = addressBuilder.setCity(CITY_NAME).build();

        Employee.Builder builder = new Employee.Builder();

        Employee employee = builder.setName("baeldung")
          .setAge(10)
          .setDesignation("author")
          .setEmail("abc@xyz.com")
          .setSupervisorName("Admin")
          .setPhoneNumber(4445566)
          .setNullablePermanent(null)
          .setDateOfJoining(Optional.empty())
          .setRole("developer")
          .addAccessTokens(1221819L)
          .addAccessTokens(1223441L, 134567L)
          .putAssetsSerialIdMapping("Laptop", 12345L)
          .setAddress(address)
          .build();

        // then
        assertTrue(employee.getAssetsSerialIdMapping().size() == 1);

    }

    @Test
    public void whenNestedBuilderTypes_thenReturnEmployeeWithValues() {

        // when
        Address.Builder addressBuilder = new Address.Builder();
        Address address = addressBuilder.setCity(CITY_NAME).build();

        Employee.Builder builder = new Employee.Builder();

        Employee employee = builder.setName("baeldung")
          .setAge(10)
          .setDesignation("author")
          .setEmail("abc@xyz.com")
          .setSupervisorName("Admin")
          .setPhoneNumber(4445566)
          .setNullablePermanent(null)
          .setDateOfJoining(Optional.empty())
          .setRole("developer")
          .addAccessTokens(1221819L)
          .addAccessTokens(1223441L, 134567L)
          .putAssetsSerialIdMapping("Laptop", 12345L)
          .setAddress(address)
          .mutateAddress(a -> a.setPinCode(112200))
          .build();

        // then
        assertTrue(employee.getAssetsSerialIdMapping().size() == 1);

    }

    @Test()
    public void whenPartialEmployeeWithValidEmail_thenReturnEmployeeWithEmail() {

        // when
        Employee.Builder builder = new Employee.Builder();

        Employee employee = builder.setName("baeldung")
          .setAge(10)
          .setEmail("abc@xyz.com")
          .buildPartial();

        assertNotNull(employee.getEmail());
    }

}
