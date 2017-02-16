package com.baeldung.jaxws.introduction.server;

import com.baeldung.jaxws.introduction.server.model.Employee;
import com.baeldung.jaxws.introduction.server.repository.EmployeeRepository;
import com.baeldung.jaxws.introduction.server.repository.EmployeeRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;


@WebService(endpointInterface = "com.baeldung.jaxws.introduction.server.EmployeeService")
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private EmployeeRepository getRepository() {
        if (null == employeeRepository) {
            employeeRepository = new EmployeeRepositoryImpl();
        }
        return employeeRepository;
    }

    @WebMethod
    public List<Employee> getAllEmployees() {
        return getRepository().getAllEmployees();
    }

    @WebMethod
    public Employee getEmployee( int id) {
        return employeeRepository.getEmployee(id);
    }


    @WebMethod
    public Employee updateEmployee(Employee employee, int id) {
        return employeeRepository.updateEmployee(employee, id);
    }


    @WebMethod
    public boolean deleteEmployee(int id) {
        return employeeRepository.deleteEmployee(id);
    }

    @WebMethod
    public Employee addEmployee(Employee employee) {
        return employeeRepository.addEmployee(new Employee(employee.getId(), employee.getFirstName()));
    }
}