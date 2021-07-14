package com.baeldung.nishit.hexagonal.adapter.primary;

import com.baeldung.nishit.hexagonal.application.port.inbound.EmployeeService;
import com.baeldung.nishit.hexagonal.domain.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeRestController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee.getName(), employee.getAge());
    }

    @DeleteMapping
    public Employee deleteEmployee(@RequestParam String id) {
        return employeeService.deleteEmployee(id);
    }

    @GetMapping
    public Employee getEmployee(@RequestParam String id) {
        return employeeService.getEmployee(id);
    }
}
