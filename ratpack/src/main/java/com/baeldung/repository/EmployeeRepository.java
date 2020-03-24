package com.baeldung.repository;

import com.baeldung.model.Employee;
import ratpack.exec.Promise;

public interface EmployeeRepository {

    Promise<Employee> findEmployeeById(Long id) throws Exception;

}
