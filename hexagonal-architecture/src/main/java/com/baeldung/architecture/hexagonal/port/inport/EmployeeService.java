package main.java.com.baeldung.architecture.hexagonal.port.inport;

import main.java.com.baeldung.architecture.hexagonal.domain.Employee;

import java.util.List;

public interface EmployeeService {

    void createEmployee(Employee employee);

    Employee getEmployee(int id);

    List<Employee> listEmployees();

}
