package com.baeldung.service;

import com.baeldung.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private Employee defaultEmployee;

    public Employee getEmployee(){
        logger.info("Returning employee for get request..."+ defaultEmployee);
        return defaultEmployee;
    }

    public boolean saveEmployee(Employee employee) {
        logger.info("Employee to save ... "+employee);
        logger.info("Employee Saved!");
        return true;
    }
}
