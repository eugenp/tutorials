package org.baeldung.boot.boottest;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    public Optional<Employee> getEmployeeById(Long id);

    public Optional<Employee> getEmployeeByName(String name);

    public List<Employee> getAllEmployees();

    public boolean exists(String email);

    public Employee save(Employee employee);
}
