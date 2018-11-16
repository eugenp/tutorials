package com.baeldung.hexagonal.output;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hexagonal.domain.Employee;

public class EmployeeLogger implements EmployeeOutput {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeLogger.class);

    @Override
    public void writeAll(List<Employee> employees) {
        employees.forEach(employee -> LOG.info(employee.toString()));
    }

}
