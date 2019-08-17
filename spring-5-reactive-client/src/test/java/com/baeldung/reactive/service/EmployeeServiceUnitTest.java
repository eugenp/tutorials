package com.baeldung.reactive.service;


import com.baeldung.reactive.model.Employee;
import com.baeldung.reactive.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceUnitTest {

    EmployeeService employeeService;
    @Mock
    private WebClient webClient;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private WebClient.RequestBodySpec requestBodySpec;
    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService(webClient);
    }

    @Test
    void givenEmployeeId_whenGetEmployeeById_thenReturnEmployee() {

        Integer employeeId = 100;
        Employee mockEmployee = new Employee(100, "Adam", "Sandler", 32, Role.LEAD_ENGINEER);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/employee/{id}", employeeId)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Employee.class)).thenReturn(Mono.just(mockEmployee));

        Mono<Employee> employeeMono = employeeService.getEmployeeById(employeeId);

        StepVerifier.create(employeeMono)
                .expectNextMatches(employee -> employee.getRole().equals(Role.LEAD_ENGINEER))
                .verifyComplete();
    }

    @Test
    void givenEmployee_whenAddEmployee_thenAddNewEmployee() {

        Employee newEmployee = new Employee(null, "Adam", "Sandler", 32, Role.LEAD_ENGINEER);
        Employee webClientResponse = new Employee(100, "Adam", "Sandler", 32, Role.LEAD_ENGINEER);
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(EmployeeService.ADD_EMPLOYEE)).thenReturn(requestBodySpec);
        when(requestBodySpec.syncBody(newEmployee)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Employee.class)).thenReturn(Mono.just(webClientResponse));

        Mono<Employee> employeeMono = employeeService.addNewEmployee(newEmployee);

        StepVerifier.create(employeeMono)
                .expectNextMatches(employee -> employee.getEmployeeId().equals(100))
                .verifyComplete();
    }

    @Test
    void givenEmployee_whenupdateEmployee_thenUpdatedEmployee() {

        Integer newAge = 33;
        String newLastName = "Sandler New";
        Employee updateEmployee = new Employee(100, "Adam", newLastName, newAge, Role.LEAD_ENGINEER);
        when(webClient.put()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(EmployeeService.PATH_PARAM_BY_ID, 100)).thenReturn(requestBodySpec);
        when(requestBodySpec.syncBody(updateEmployee)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Employee.class)).thenReturn(Mono.just(updateEmployee));

        Mono<Employee> updatedEmployee = employeeService.updateEmployee(100, updateEmployee);

        StepVerifier.create(updatedEmployee)
                .expectNextMatches(employee -> employee.getLastName().equals(newLastName) && employee.getAge() == newAge)
                .verifyComplete();

    }

    @Test
    void givenEmployee_whenDeleteEmployeeById_thenDeleteSuccessful() {

        String responseMessage = "Employee Deleted SuccessFully";
        when(webClient.delete()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(EmployeeService.PATH_PARAM_BY_ID, 100)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(responseMessage));

        Mono<String> deletedEmployee = employeeService.deleteEmployeeById(100);

        StepVerifier.create(deletedEmployee)
                .expectNext(responseMessage)
                .verifyComplete();
    }
}