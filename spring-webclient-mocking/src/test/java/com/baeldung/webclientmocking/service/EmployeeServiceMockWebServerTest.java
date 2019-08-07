package com.baeldung.webclientmocking.service;

import com.baeldung.webclientmocking.domain.Employee;
import com.baeldung.webclientmocking.enums.Role;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class EmployeeServiceMockWebServerTest {

    @Rule
    public final MockWebServer mockBackEnd = new MockWebServer();
    private EmployeeService employeeService;
    ObjectMapper objectMapper ;

    @BeforeEach
    public void setUp(){

        final String baseUrl=String.format("http://localhost:%s", mockBackEnd.getPort());
        employeeService = new EmployeeService(baseUrl);
        objectMapper = new ObjectMapper();
    }

    @Test
    void getEmployeeById() throws JsonProcessingException, InterruptedException {

        //given
        Integer employeeId = 100;
        Employee mockEmployee = new Employee(100, "Adam", "Sandler", 32, Role.LEAD_ENGINEER);
        mockBackEnd.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(mockEmployee))
                .addHeader("Content-Type", "application/json"));

        //when
        Mono<Employee> employeeMono = employeeService.getEmployeeById(employeeId);

        //then
        StepVerifier.create(employeeMono)
                .expectNextMatches(employee -> employee.getRole().equals(Role.LEAD_ENGINEER))
                .verifyComplete();

        //verify
        final RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        Assertions.assertEquals(recordedRequest.getMethod(),"GET");
        Assertions.assertEquals((recordedRequest.getPath()),"/employee/100");
    }

    @Test
    void addNewEmployee() throws JsonProcessingException, InterruptedException {

        //given
        Employee newEmployee = new Employee(null, "Adam", "Sandler", 32, Role.LEAD_ENGINEER);
        Employee webClientResponse = new Employee(100, "Adam", "Sandler", 32, Role.LEAD_ENGINEER);
        mockBackEnd.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(webClientResponse))
                .addHeader("Content-Type", "application/json"));

        //when
        Mono<Employee> employeeMono = employeeService.addNewEmployee(newEmployee);

        //then
        StepVerifier.create(employeeMono)
                .expectNextMatches(employee -> employee.getEmployeeId().equals(100))
                .verifyComplete();

        //verify
        final RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        Assertions.assertEquals(recordedRequest.getMethod(),"POST");
        Assertions.assertEquals((recordedRequest.getPath()),"/employee");
    }

    @Test
    void updateEmployee() throws JsonProcessingException, InterruptedException {

        //given
        Integer employeeId=100;
        Integer newAge=33;
        String newLastName="Sandler New";
        Employee updateEmployee = new Employee(100, "Adam", newLastName, newAge, Role.LEAD_ENGINEER);
        mockBackEnd.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(updateEmployee))
                .addHeader("Content-Type", "application/json"));
        //when
        Mono<Employee> updatedEmploye =employeeService.updateEmployee(employeeId, updateEmployee);

        //then
        StepVerifier.create(updatedEmploye)
                .expectNextMatches(employee -> employee.getLastName().equals(newLastName) && employee.getAge() == newAge)
                .verifyComplete();

        //verify
        final RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        Assertions.assertEquals(recordedRequest.getMethod(),"PUT");
        Assertions.assertEquals((recordedRequest.getPath()),"/employee/100");

    }


    @Test
    void deleteEmployee() throws JsonProcessingException, InterruptedException {

        //given
        String responseMessage = "Employee Deleted SuccessFully";
        Integer employeeId=100;
        mockBackEnd.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(responseMessage))
                .addHeader("Content-Type", "application/json"));
        //when
        Mono<String> deletedEmployee = employeeService.deleteEmployeeById(employeeId);

        //then
        StepVerifier.create(deletedEmployee)
                .expectNext("\"Employee Deleted SuccessFully\"")
                .verifyComplete();

        //verify
        final RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        Assertions.assertEquals(recordedRequest.getMethod(),"DELETE");
        Assertions.assertEquals((recordedRequest.getPath()),"/employee/100");
    }

}
