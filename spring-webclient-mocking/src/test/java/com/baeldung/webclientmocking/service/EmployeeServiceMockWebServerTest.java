package com.baeldung.webclientmocking.service;

import com.baeldung.webclientmocking.domain.Employee;
import com.baeldung.webclientmocking.enums.Role;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Rule;
import org.junit.jupiter.api.*;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceMockWebServerTest {

    public  static MockWebServer mockBackEnd=null;
    private EmployeeService employeeService;
    ObjectMapper objectMapper;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    void initialize(){

        final String baseUrl=String.format("http://localhost:%s", mockBackEnd.getPort());
        employeeService = new EmployeeService(baseUrl);
        objectMapper = new ObjectMapper();
    }

    @Test
    void getEmployeeById() throws Exception {

        Integer employeeId = 100;
        Employee mockEmployee = new Employee(100, "Adam", "Sandler", 32, Role.LEAD_ENGINEER);
        mockBackEnd.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(mockEmployee))
                .addHeader("Content-Type", "application/json"));

        Mono<Employee> employeeMono = employeeService.getEmployeeById(employeeId);

        StepVerifier.create(employeeMono)
                .expectNextMatches(employee -> employee.getRole().equals(Role.LEAD_ENGINEER))
                .verifyComplete();

        final RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertEquals(recordedRequest.getMethod(),"GET");
        assertEquals((recordedRequest.getPath()),"/employee/100");
    }

    @Test
    void addNewEmployee() throws Exception {

        Employee newEmployee = new Employee(null, "Adam", "Sandler", 32, Role.LEAD_ENGINEER);
        Employee webClientResponse = new Employee(100, "Adam", "Sandler", 32, Role.LEAD_ENGINEER);
        mockBackEnd.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(webClientResponse))
                .addHeader("Content-Type", "application/json"));

        Mono<Employee> employeeMono = employeeService.addNewEmployee(newEmployee);

        StepVerifier.create(employeeMono)
                .expectNextMatches(employee -> employee.getEmployeeId().equals(100))
                .verifyComplete();

        final RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertEquals(recordedRequest.getMethod(),"POST");
        assertEquals((recordedRequest.getPath()),"/employee");
    }

    @Test
    void updateEmployee() throws Exception {

        Integer employeeId=100;
        Integer newAge=33;
        String newLastName="Sandler New";
        Employee updateEmployee = new Employee(100, "Adam", newLastName, newAge, Role.LEAD_ENGINEER);
        mockBackEnd.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(updateEmployee))
                .addHeader("Content-Type", "application/json"));

        Mono<Employee> updatedEmploye =employeeService.updateEmployee(employeeId, updateEmployee);

        StepVerifier.create(updatedEmploye)
                .expectNextMatches(employee -> employee.getLastName().equals(newLastName) && employee.getAge() == newAge)
                .verifyComplete();

        final RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertEquals(recordedRequest.getMethod(),"PUT");
        assertEquals((recordedRequest.getPath()),"/employee/100");

    }


    @Test
    void deleteEmployee() throws Exception {

        String responseMessage = "Employee Deleted SuccessFully";
        Integer employeeId=100;
        mockBackEnd.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(responseMessage))
                .addHeader("Content-Type", "application/json"));

        Mono<String> deletedEmployee = employeeService.deleteEmployeeById(employeeId);

        StepVerifier.create(deletedEmployee)
                .expectNext("\"Employee Deleted SuccessFully\"")
                .verifyComplete();

        final RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertEquals(recordedRequest.getMethod(),"DELETE");
        assertEquals((recordedRequest.getPath()),"/employee/100");
    }

}
