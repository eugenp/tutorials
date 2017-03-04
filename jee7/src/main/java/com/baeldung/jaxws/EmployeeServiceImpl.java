package com.baeldung.jaxws;

import com.baeldung.jaxws.model.Employee;
import com.baeldung.jaxws.repository.EmployeeRepository;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService(endpointInterface = "com.baeldung.jaxws.EmployeeService")
public class EmployeeServiceImpl implements EmployeeService {

    @Inject private EmployeeRepository employeeRepositoryImpl;

    @WebMethod
    public Employee getEmployee(int id) {
        return employeeRepositoryImpl.getEmployee(id);
    }

    @WebMethod
    public Employee updateEmployee(int id, String name) {
        return employeeRepositoryImpl.updateEmployee(id, name);
    }

    @WebMethod
    public boolean deleteEmployee(int id) {
        return employeeRepositoryImpl.deleteEmployee(id);
    }

    @WebMethod
    public Employee addEmployee(int id, String name) {
        return employeeRepositoryImpl.addEmployee(id, name);
    }

    @WebMethod
    public int countEmployees() {
        return employeeRepositoryImpl.count();
    }

    @WebMethod
    public List<Employee> getAllEmployees() {
        return employeeRepositoryImpl.getAllEmployees();
    }
}
