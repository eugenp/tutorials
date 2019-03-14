package com.baeldung.projections;

import com.baeldung.models.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "employeeDto", types = { Employee.class })
public interface EmployeeDto {

    @Value("#{target.id}")
    long getId();

    String getName();

    int getAge();
}
