package com.baeldung.domain;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceUnitTest {

    @Mock
    private EmployeeRepositoryPort employeeRepositoryPort;
    @InjectMocks
    private EmployeeService adapter;

    @Test
    public void whenGettingAllEmployeesWillReturnAListWithOneElement() {
        Employee employee = Employee.builder()
            .withEmployeeId("ABC123")
            .withName("Bob Doe")
            .build();

        when(employeeRepositoryPort.findAll()).thenReturn(Arrays.asList(employee));

        List<Employee> result = adapter.getAll();
        assertThat(result, hasSize(1));
        assertThat(result, containsInAnyOrder(employee));
    }
}
