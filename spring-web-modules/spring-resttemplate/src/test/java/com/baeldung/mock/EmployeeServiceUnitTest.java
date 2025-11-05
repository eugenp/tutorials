package com.baeldung.mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalMatchers.eq;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.baeldung.resttemplate.web.model.Employee;

@ExtendWith(MockitoExtension.class) 
public class EmployeeServiceUnitTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EmployeeService empService = new EmployeeService();

    @Test
    public void givenMockingIsDoneByMockito_whenGetForEntityIsCalled_thenReturnMockedObject() throws Exception {
        Employee emp = new Employee("E001", "Eric Simmons");
        Mockito.when(restTemplate.getForEntity("http://localhost:8080/employee/E001", Employee.class))
          .thenReturn(new ResponseEntity(emp, HttpStatus.OK));

        Employee employee = empService.getEmployeeWithGetForEntity("E001");

        assertEquals(emp, employee);
    }

    @Test
    public void givenMockingIsDoneByMockito_whenExchangeIsCalled_thenReturnMockedObject(){
        Employee emp = new Employee("E001", "Eric Simmons");
        Mockito.when(restTemplate.exchange("http://localhost:8080/employee/E001",
            HttpMethod.GET,
            null,
            Employee.class)
        ).thenReturn(new ResponseEntity(emp, HttpStatus.OK));
        Employee employee = empService.getEmployeeWithRestTemplateExchange("E001");
        assertEquals(emp, employee);
    }
}
