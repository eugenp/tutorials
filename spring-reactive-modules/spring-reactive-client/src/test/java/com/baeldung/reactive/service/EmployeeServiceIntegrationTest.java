package com.baeldung.reactive.service;

import com.baeldung.reactive.model.Employee;
import com.baeldung.reactive.enums.Role;
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

class EmployeeServiceIntegrationTest {

    public static MockWebServer mockBackEnd;
    private EmployeeService employeeService;
    private ObjectMapper MAPPER = new ObjectMapper();

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
    void initialize() {

        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        employeeService = new EmployeeService(baseUrl);
    }

    @Test
    void getEmployeeById() throws Exception {

        Employee mockEmployee = new Employee(100, "Adam", "Sandler", 32, Role.LEAD_ENGINEER);
        mockBackEnd.enqueue(new MockResponse().setBody(MAPPER.writeValueAsString(mockEmployee))
                .addHeader("Content-Type", "application/json"));

        Mono<Employee> employeeMono = employeeService.getEmployeeById(100);

        StepVerifier.create(employeeMono)
                .expectNextMatches(employee -> employee.getRole().equals(Role.LEAD_ENGINEER))
                .verifyComplete();

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/employee/100", recordedRequest.getPath());
    }

    @Test
    void addNewEmployee() throws Exception {

        Employee newEmployee = new Employee(null, "Adam", "Sandler", 32, Role.LEAD_ENGINEER);
        Employee webClientResponse = new Employee(100, "Adam", "Sandler", 32, Role.LEAD_ENGINEER);
        mockBackEnd.enqueue(new MockResponse().setBody(MAPPER.writeValueAsString(webClientResponse))
                .addHeader("Content-Type", "application/json"));

        Mono<Employee> employeeMono = employeeService.addNewEmployee(newEmployee);

        StepVerifier.create(employeeMono)
                .expectNextMatches(employee -> employee.getEmployeeId().equals(100))
                .verifyComplete();

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertEquals("POST", recordedRequest.getMethod());
        assertEquals("/employee", recordedRequest.getPath());
    }

    @Test
    void updateEmployee() throws Exception {

        Integer newAge = 33;
        String newLastName = "Sandler New";
        Employee updateEmployee = new Employee(100, "Adam", newLastName, newAge, Role.LEAD_ENGINEER);
        mockBackEnd.enqueue(new MockResponse().setBody(MAPPER.writeValueAsString(updateEmployee))
                .addHeader("Content-Type", "application/json"));

        Mono<Employee> updatedEmploye = employeeService.updateEmployee(100, updateEmployee);

        StepVerifier.create(updatedEmploye)
                .expectNextMatches(employee -> employee.getLastName().equals(newLastName) && employee.getAge() == newAge)
                .verifyComplete();

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertEquals("PUT", recordedRequest.getMethod());
        assertEquals("/employee/100", recordedRequest.getPath());

    }


    @Test
    void deleteEmployee() throws Exception {

        String responseMessage = "Employee Deleted SuccessFully";
        Integer employeeId = 100;
        mockBackEnd.enqueue(new MockResponse().setBody(MAPPER.writeValueAsString(responseMessage))
                .addHeader("Content-Type", "application/json"));

        Mono<String> deletedEmployee = employeeService.deleteEmployeeById(employeeId);

        StepVerifier.create(deletedEmployee)
                .expectNext("\"Employee Deleted SuccessFully\"")
                .verifyComplete();

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertEquals("DELETE", recordedRequest.getMethod());
        assertEquals("/employee/100", recordedRequest.getPath());
    }

}