package org.baeldung.web.service;

import org.baeldung.web.dto.EmployeeDto;
import org.baeldung.web.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeService {

    static final String EMP_URL_PREFIX = "http://localhost:8080/employee";
    static final String URL_SEP = "/";

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private RestTemplate restTemplate;

    public EmployeeDto getEmployee(String id) throws Exception {

        Employee emp = null;
        try {

            ResponseEntity<Employee> resp = restTemplate.getForEntity(EMP_URL_PREFIX
              + URL_SEP + id, Employee.class);

            if (resp == null || resp.getStatusCode() != HttpStatus.OK
              || resp.getBody() == null) {

                throw new Exception("Employee details could not be fetched.");
            }

            emp = resp.getBody();

            EmployeeDto dto = new EmployeeDto();
            dto.setId(emp.getId());
            dto.setName(emp.getName());
            dto.setSalary(emp.getSalary());
            return dto;

        } catch (Exception e) {
            logger.error("Error occurred while fetching employee details", e);
            throw new Exception("Error occurred while fetching employee details", e);
        }
    }
}
