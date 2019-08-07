package com.baeldung.webclientmocking.service;

import com.baeldung.webclientmocking.domain.Employee;
import com.baeldung.webclientmocking.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {

    EmployeeService employeeService;
    private WebClient webClient = mock(WebClient.class);
    private WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
    private WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
    private WebClient.RequestBodyUriSpec requestBodyUriSpec = mock(WebClient.RequestBodyUriSpec.class);
    private WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);


    @BeforeEach
    public void setUp(){

        employeeService = new EmployeeService(webClient);
    }

    @Test
    void getEmployeeById(){

        //given
        Integer employeeId = 100;
        Employee mockEmployee = new Employee(100, "Adam", "Sandler", 32, Role.LEAD_ENGINEER);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(EmployeeService.PATH_PARAM_BY_ID, employeeId)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Employee.class)).thenReturn(Mono.just(mockEmployee));

        //when
        Mono<Employee> employeeMono = employeeService.getEmployeeById(employeeId);

        //then
        StepVerifier.create(employeeMono)
                .expectNextMatches(employee -> employee.getRole().equals(Role.LEAD_ENGINEER))
                .verifyComplete();
    }

    @Test
    void addNewEmployee(){

        //given
        Employee newEmployee = new Employee(null, "Adam", "Sandler", 32, Role.LEAD_ENGINEER);
        Employee webClientResponse = new Employee(100, "Adam", "Sandler", 32, Role.LEAD_ENGINEER);
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(EmployeeService.ADD_EMPLOYEE)).thenReturn(requestBodySpec);
        when(requestBodySpec.syncBody(newEmployee)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Employee.class)).thenReturn(Mono.just(webClientResponse));

        //when
        Mono<Employee> employeeMono = employeeService.addNewEmployee(newEmployee);

        //then
        StepVerifier.create(employeeMono)
                .expectNextMatches(employee -> employee.getEmployeeId().equals(100))
                .verifyComplete();
    }

    @Test
    void updateEmployee(){

        //given
        Integer employeeId=100;
        Integer newAge=33;
        String newLastName="Sandler New";
        Employee updateEmployee = new Employee(100, "Adam", newLastName, newAge, Role.LEAD_ENGINEER);
        when(webClient.put()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(EmployeeService.PATH_PARAM_BY_ID,employeeId)).thenReturn(requestBodySpec);
        when(requestBodySpec.syncBody(updateEmployee)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Employee.class)).thenReturn(Mono.just(updateEmployee));

        //when
        Mono<Employee> updatedEmploye =employeeService.updateEmployee(employeeId, updateEmployee);

        //then
        StepVerifier.create(updatedEmploye)
                .expectNextMatches(employee -> employee.getLastName().equals(newLastName) && employee.getAge() == newAge)
                .verifyComplete();

    }

    @Test
    void deleteEmployee(){

        //given
        String responseMessage = "Employee Deleted SuccessFully";
        Integer employeeId=100;
        when(webClient.delete()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(EmployeeService.PATH_PARAM_BY_ID, employeeId)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(responseMessage));

        //when
        Mono<String> deletedEmployee = employeeService.deleteEmployeeById(employeeId);

        //then
        StepVerifier.create(deletedEmployee)
                .expectNext(responseMessage)
                .verifyComplete();
    }


    }
