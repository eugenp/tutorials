package com.baeldung.hexagonal.architecture;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.baeldung.hexagonal.architecture.core.BirthdayService;
import com.baeldung.hexagonal.architecture.core.EmployeeStore;

public class HexagonalArchitectureIntegrationTest {

    private static BirthdayService birthdayService;

    @BeforeAll
    public static void init() {
        EmployeeStore store = new FileEmployeeStore("src/test/resources/files/hexagonal/employees.csv");
        birthdayService = new BirthdayService(store);
    }

    @Test
    public void givenListOfEmployees_whenRunningService_thenFilterOneOutOfTheList() {

        assertThat(birthdayService.findMonthlyBirthdayEmployees()
            .size()).isEqualTo(2);
    }
}
