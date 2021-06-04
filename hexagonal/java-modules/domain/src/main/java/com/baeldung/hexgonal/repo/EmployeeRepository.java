package com.baeldung.hexgonal.repo;


import com.baeldung.hexgonal.data.Employee;

public interface EmployeeRepository {
    void save(Employee employee);
    void delete(Long id);
}
