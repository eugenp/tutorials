package main.java.com.baeldung.architecture.hexagonal.service;

import main.java.com.baeldung.architecture.hexagonal.domain.Employee;
import main.java.com.baeldung.architecture.hexagonal.port.inport.EmployeeService;
import main.java.com.baeldung.architecture.hexagonal.port.outport.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Override
    public void createEmployee(Employee employee) {
        employeeRepo.createEmployee(employee);
    }

    @Override
    public Employee getEmployee(int id) {
        return employeeRepo.getEmployee(id);
    }

    @Override
    public List<Employee> listEmployees() {
        return employeeRepo.listEmployees();
    }

}
