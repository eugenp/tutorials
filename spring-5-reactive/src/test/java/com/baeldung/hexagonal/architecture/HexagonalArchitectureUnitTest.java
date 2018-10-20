package com.baeldung.hexagonal.architecture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.baeldung.hexagonal.architecture.core.BirthdayService;
import com.baeldung.hexagonal.architecture.core.Employee;
import com.baeldung.hexagonal.architecture.core.EmployeeStore;

public class HexagonalArchitectureUnitTest {

    private static BirthdayService birthdayService;

    private static EmployeeStore store;

    @Test
    public void givenListOfEmployees_whenRunningService_thenFilterOneOutOfTheList() {

        store = mock(EmployeeStore.class);
        when(store.getEmployees()).thenReturn(getlist());
        birthdayService = new BirthdayService(store);

        assertThat(birthdayService.findMonthlyBirthdayEmployees()
            .size()).isEqualTo(1);
    }

    @Test
    public void givenEmptyEmployees_whenRunningService_thenh() {

        store = mock(EmployeeStore.class);
        when(store.getEmployees()).thenReturn(new ArrayList<>());
        birthdayService = new BirthdayService(store);

        assertThat(birthdayService.findMonthlyBirthdayEmployees()
            .size()).isEqualTo(0);
    }

    private static List<Employee> getlist() {
        List<Employee> list = new ArrayList<>();
        list.add(new Employee(1, "1", LocalDate.of(1990, 1, 2)));
        list.add(new Employee(2, "2", LocalDate.of(1990, 2, 2)));
        list.add(new Employee(3, "3", LocalDate.of(1990, 3, 2)));
        list.add(new Employee(4, "4", LocalDate.of(1990, 4, 2)));
        list.add(new Employee(5, "5", LocalDate.of(1990, 5, 2)));
        list.add(new Employee(6, "6", LocalDate.of(1990, 6, 2)));
        list.add(new Employee(7, "7", LocalDate.of(1990, 7, 2)));
        list.add(new Employee(8, "8", LocalDate.of(1990, 8, 2)));
        list.add(new Employee(9, "9", LocalDate.of(1990, 9, 2)));
        list.add(new Employee(10, "10", LocalDate.of(1990, 10, 2)));
        list.add(new Employee(11, "11", LocalDate.of(1990, 11, 2)));
        list.add(new Employee(12, "12", LocalDate.of(1990, 12, 2)));
        return list;
    }

}
