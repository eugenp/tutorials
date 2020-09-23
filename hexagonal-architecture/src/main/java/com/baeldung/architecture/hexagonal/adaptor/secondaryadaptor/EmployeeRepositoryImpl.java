package main.java.com.baeldung.architecture.hexagonal.adaptor.secondaryadaptor;

import main.java.com.baeldung.architecture.hexagonal.domain.Employee;
import main.java.com.baeldung.architecture.hexagonal.port.outport.EmployeeRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final HashMap<Integer, Employee> employeeDatabase = new HashMap<Integer, Employee>();

    @Override
    public void createEmployee(Employee employee) {
        employeeDatabase.put(employee.getId(), employee);
    }

    @Override
    public Employee getEmployee(int id) {
        return employeeDatabase.get(id);
    }

    @Override
    public List<Employee> listEmployees() {
        return new ArrayList<>(employeeDatabase.values());
    }

}
