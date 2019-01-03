package com.baeldung.hexagonal.api.jmx;

import com.baeldung.hexagonal.api.EmployeeApi;
import com.baeldung.hexagonal.model.Employee;
import com.baeldung.hexagonal.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("api-jmx")
@Component
@ManagedResource
public class EmployeesJmxResource implements EmployeeApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeesJmxResource.class) ;

    @Autowired
    private EmployeeService employeeService;

    @Override
    @ManagedOperation
    public Employee create(String nameFirst, String nameLast) {
        LOGGER.info("create({}, {})", nameFirst, nameLast) ;
        return employeeService.create(nameFirst, nameLast) ;
    }

    @Override
    @ManagedOperation
    public List<Employee> findAll() {
        LOGGER.info("findAll()") ;
        return employeeService.findAll() ;
    }
}
