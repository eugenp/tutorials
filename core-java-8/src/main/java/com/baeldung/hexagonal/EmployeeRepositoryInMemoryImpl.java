package com.baeldung.hexagonal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Adapter (concrete implementation) of @{@link EmployeeRepository} interface.
 */
public class EmployeeRepositoryInMemoryImpl implements EmployeeRepository {

    private List<Employee> employeeList = new ArrayList<>();

    @Override
    public void save(Employee employee) {
        employeeList.add(employee);
    }

    @Override
    public Optional<Employee> findById(Integer employeeId) {
        return employeeList.stream().filter(employee -> employee.getId().equals(employeeId)).findFirst();
    }

}
