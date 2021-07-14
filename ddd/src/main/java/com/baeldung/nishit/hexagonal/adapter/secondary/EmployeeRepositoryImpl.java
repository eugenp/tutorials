package com.baeldung.nishit.hexagonal.adapter.secondary;

import com.baeldung.nishit.hexagonal.application.port.outbound.EmployeeRepository;
import com.baeldung.nishit.hexagonal.domain.model.Employee;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private Map<String, Employee> employeeMap = new HashMap<>();

    @Override
    public Employee addEmployee(Employee e) {
        employeeMap.put(e.getId(), e);
        return employeeMap.get(e.getId());
    }

    @Override
    public Employee getEmployee(String id) {
        return employeeMap.get(id);
    }

    @Override
    public Employee deleteEmployee(String id) {
        return employeeMap.remove(id);
    }
}
