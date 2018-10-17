package org.baeldung.web.service;

import org.baeldung.web.dto.EmployeeDto;
import org.baeldung.web.model.Employee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class EmployeeServiceUnitTest {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceUnitTest.class);

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EmployeeService empService = new EmployeeService();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenMockingIsDoneByMockito_whenGetIsCalled_shouldReturnMockedObject() throws Exception {
        String id = "E001";
        Employee emp = new Employee();
        emp.setId(id);
        emp.setName("Eric Simmons");
        emp.setSalary(10000.00d);
        Mockito
          .when(restTemplate.getForEntity(EmployeeService.EMP_URL_PREFIX
            + EmployeeService.URL_SEP + id, Employee.class))
          .thenReturn(new ResponseEntity<Employee>(emp, HttpStatus.OK));

        EmployeeDto employeeDto = empService.getEmployee(id);
        logger.info("Employee received as: {}", employeeDto);
        Assert.assertEquals(emp.getName(), employeeDto.getName());
        Assert.assertEquals(emp.getSalary(), employeeDto.getSalary());
    }

}
