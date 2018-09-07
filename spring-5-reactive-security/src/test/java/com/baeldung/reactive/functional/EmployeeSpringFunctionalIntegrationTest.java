package com.baeldung.reactive.functional;

import org.junit.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.webflux.Employee;

public class EmployeeSpringFunctionalIntegrationTest {

    private static EmployeeFunctionalConfig config = new EmployeeFunctionalConfig();

    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenCorrectEmployee() {
        WebTestClient client = WebTestClient
            .bindToRouterFunction(config.getEmployeeByIdRoute())
            .build();

        Employee expected = new Employee("1", "Employee 1");

        client.get()
            .uri("/employees/1")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(Employee.class)
            .isEqualTo(expected);
    }
}
