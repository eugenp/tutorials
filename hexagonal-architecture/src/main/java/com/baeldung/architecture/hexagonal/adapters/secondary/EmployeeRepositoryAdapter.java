package com.baeldung.architecture.hexagonal.adapters.secondary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.baeldung.architecture.hexagonal.domain.Employee;
import com.baeldung.architecture.hexagonal.port.outbound.EmployeeRepository;

@Repository
public class EmployeeRepositoryAdapter implements EmployeeRepository {

    private final HashMap<Integer, Employee> employeeHashMap = new HashMap<Integer, Employee>();

    @Override
    public List<Employee> fetchAllEmployees() {
        return new ArrayList<>(employeeHashMap.values());
    }

    @Override
    public Employee fetchEmployee(int id) {
        return employeeHashMap.get(id);
    }

    @Override
    public void insertEmployee(Employee employee) {
        String name = employee.getName();
        System.out.println(name);
        employeeHashMap.put(employee.getId(), employee);
    }

}
