package com.baeldung.port;

import java.util.Optional;
import com.baeldung.domain.Employee;

public interface EmployeeDetailsPort {

    Employee create(String name, String role, long salary);

    Optional<Employee> getEmployee(Integer userId);
}