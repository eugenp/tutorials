package main.java.com.baeldung.architecture.hexagonal.port.outport;

import main.java.com.baeldung.architecture.hexagonal.domain.Employee;

import java.util.List;

public interface EmployeeRepository {

    void createEmployee(Employee employee);

    Employee getEmployee(int id);

    List<Employee> listEmployees();

}
