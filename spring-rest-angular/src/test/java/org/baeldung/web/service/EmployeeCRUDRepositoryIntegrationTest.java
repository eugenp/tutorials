package org.baeldung.web.service;

import static org.junit.Assert.*;

import org.baeldung.web.entity.Employee;
import org.baeldung.web.main.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class EmployeeCRUDRepositoryIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    private static final String EMPLOYEE_ENDPOINT = "http://localhost:8080/employees/";
    private static int EMPLOYEE_ID = 1;
    private static int EMPLOYEE_AGE = 25;

    @Test
    public void whenEmployeeCRUDOperations_thenCorrect() {
        Employee Employee = new Employee(EMPLOYEE_ID, "Bryan", 20);
        ResponseEntity<Employee> postResponse = template.postForEntity(EMPLOYEE_ENDPOINT, Employee, Employee.class, "");
        assertEquals("status is not 201", HttpStatus.CREATED, postResponse.getStatusCode());

        Employee.setAge(EMPLOYEE_AGE);
        Employee patchResponse = template.patchForObject(EMPLOYEE_ENDPOINT + "/" + EMPLOYEE_ID, Employee, Employee.class);
        assertEquals("age is not 25", Integer.valueOf(EMPLOYEE_AGE), patchResponse.getAge());

        ResponseEntity<Employee> getResponse = template.getForEntity(EMPLOYEE_ENDPOINT + "/" + EMPLOYEE_ID, Employee.class, "");
        assertEquals("status is not 200", HttpStatus.OK, getResponse.getStatusCode());

        template.delete(EMPLOYEE_ENDPOINT + "/" + EMPLOYEE_ID);

        getResponse = template.getForEntity(EMPLOYEE_ENDPOINT + "/" + EMPLOYEE_ID, Employee.class, "");
        assertEquals("status is not 404", HttpStatus.NOT_FOUND, getResponse.getStatusCode());

    }
}
