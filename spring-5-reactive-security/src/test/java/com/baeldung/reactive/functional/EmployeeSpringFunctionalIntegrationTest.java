package com.baeldung.reactive.functional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.webflux.Employee;
import com.baeldung.webflux.EmployeeRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = EmployeeSpringFunctionalApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeSpringFunctionalIntegrationTest {

    @Autowired
    private EmployeeFunctionalConfig config;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenCorrectEmployee() {
        WebTestClient client = WebTestClient
            .bindToRouterFunction(config.getEmployeeByIdRoute())
            .build();

        Employee employee = new Employee("1", "Employee 1");

        given(employeeRepository.findEmployeeById("1")).willReturn(Mono.just(employee));

        client.get()
            .uri("/employees/1")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(Employee.class)
            .isEqualTo(employee);
    }

    @Test
    public void whenGetAllEmployees_thenCorrectEmployees() {
        WebTestClient client = WebTestClient
            .bindToRouterFunction(config.getAllEmployeesRoute())
            .build();

        List<Employee> employeeList = new ArrayList<>();

        Employee employee1 = new Employee("1", "Employee 1");
        Employee employee2 = new Employee("2", "Employee 2");

        employeeList.add(employee1);
        employeeList.add(employee2);

        Flux<Employee> employeeFlux = Flux.fromIterable(employeeList);
        given(employeeRepository.findAllEmployees()).willReturn(employeeFlux);

        client.get()
            .uri("/employees")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(Employee.class)
            .isEqualTo(employeeList);
    }

    @Test
    public void whenUpdateEmployee_thenEmployeeUpdated() {
        WebTestClient client = WebTestClient
            .bindToRouterFunction(config.updateEmployeeRoute())
            .build();

        Employee employee = new Employee("1", "Employee 1 Updated");

        client.post()
            .uri("/employees/update")
            .body(Mono.just(employee), Employee.class)
            .exchange()
            .expectStatus()
            .isOk();

        verify(employeeRepository).updateEmployee(employee);
    }
}
