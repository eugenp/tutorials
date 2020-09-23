package main.java.com.baeldung.architecture.hexagonal.adaptor.primaryadaptor;

import main.java.com.baeldung.architecture.hexagonal.domain.Employee;
import main.java.com.baeldung.architecture.hexagonal.port.inport.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Employee")
public class EmployeeRestController {

    @Autowired
    public EmployeeService employeeService;

    @PostMapping
    public void createEmployee(@RequestBody Employee employee) {
        employeeService.createEmployee(employee);
    }


    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable(value = "id") int id) {
        return employeeService.getEmployee(id);
    }

    @GetMapping
    public List<Employee> listEmployee() {
        return employeeService.listEmployees();
    }

}
