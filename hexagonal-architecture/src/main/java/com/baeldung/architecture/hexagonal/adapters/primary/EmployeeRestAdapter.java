package com.baeldung.architecture.hexagonal.adapters.primary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.architecture.hexagonal.domain.Employee;
import com.baeldung.architecture.hexagonal.port.inbound.HandleUserInteraction;
import com.baeldung.architecture.hexagonal.port.outbound.EmployeeRepository;

@RestController
@RequestMapping("/Employee")
public class EmployeeRestAdapter implements HandleUserInteraction {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    @Override
    public List<Employee> showAllEmployee() {
        return employeeRepository.fetchAllEmployees();
    }

    @GetMapping("/{id}")
    @Override
    public Employee getEmployeeById(@PathVariable(value = "id") int id) {
        return employeeRepository.fetchEmployee(id);
    }

    @PostMapping
    @Override
    public void addNewEmployee(@RequestBody Employee employee) {
        String name = employee.getName();
        System.out.println(name);
        employeeRepository.insertEmployee(employee);
    }

}
