package com.baeldung.web.service;

import com.baeldung.resttemplate.web.model.Employee;
import com.baeldung.resttemplate.web.service.EmployeeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceUnitTest {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceUnitTest.class);

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EmployeeService empService = new EmployeeService();

    @Test
    public void givenMockingIsDoneByMockito_whenGetIsCalled_shouldReturnMockedObject() throws Exception {
        Employee emp = new Employee("E001", "Eric Simmons");
        Mockito.when(restTemplate.getForEntity("http://localhost:8080/employee/E001", Employee.class))
          .thenReturn(new ResponseEntity(emp, HttpStatus.OK));

        Employee employee = empService.getEmployee("E001");

        Assert.assertEquals(emp, employee);
    }

}
