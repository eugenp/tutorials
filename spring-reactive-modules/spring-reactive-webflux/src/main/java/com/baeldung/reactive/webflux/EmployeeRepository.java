package com.baeldung.reactive.webflux;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Repository
public class EmployeeRepository {

    private static final Map<String, Employee> EMPLOYEE_DATA;

    static {
        EMPLOYEE_DATA = new HashMap<>();
        EMPLOYEE_DATA.put("1", new Employee("1", "Employee 1"));
        EMPLOYEE_DATA.put("2", new Employee("2", "Employee 2"));
        EMPLOYEE_DATA.put("3", new Employee("3", "Employee 3"));
        EMPLOYEE_DATA.put("4", new Employee("4", "Employee 4"));
        EMPLOYEE_DATA.put("5", new Employee("5", "Employee 5"));
        EMPLOYEE_DATA.put("6", new Employee("6", "Employee 6"));
        EMPLOYEE_DATA.put("7", new Employee("7", "Employee 7"));
        EMPLOYEE_DATA.put("8", new Employee("8", "Employee 8"));
        EMPLOYEE_DATA.put("9", new Employee("9", "Employee 9"));
        EMPLOYEE_DATA.put("10", new Employee("10", "Employee 10"));
    }

    public Mono<Employee> findEmployeeById(String id) {
        return Mono.just(EMPLOYEE_DATA.get(id));
    }

    public Flux<Employee> findAllEmployees() {
        return Flux.fromIterable(EMPLOYEE_DATA.values());
    }

    public Mono<Employee> updateEmployee(Employee employee) {
        Employee existingEmployee = EMPLOYEE_DATA.get(employee.getId());
        if (existingEmployee != null) {
            existingEmployee.setName(employee.getName());
        }
        return Mono.just(existingEmployee);
    }
}
