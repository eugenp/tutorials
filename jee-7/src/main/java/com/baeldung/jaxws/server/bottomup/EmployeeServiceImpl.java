package com.baeldung.jaxws.server.bottomup;

import java.util.List;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;

import com.baeldung.jaxws.server.bottomup.exception.EmployeeAlreadyExists;
import com.baeldung.jaxws.server.bottomup.exception.EmployeeNotFound;
import com.baeldung.jaxws.server.bottomup.model.Employee;
import com.baeldung.jaxws.server.repository.EmployeeRepository;

@WebService(serviceName = "EmployeeService", endpointInterface = "com.baeldung.jaxws.server.bottomup.EmployeeService")
public class EmployeeServiceImpl implements EmployeeService {

    @Inject
    private EmployeeRepository employeeRepositoryImpl;

    @WebMethod
    public Employee getEmployee(int id) throws EmployeeNotFound {
        return employeeRepositoryImpl.getEmployee(id);
    }

    @WebMethod
    public Employee updateEmployee(int id, String name) throws EmployeeNotFound {
        return employeeRepositoryImpl.updateEmployee(id, name);
    }

    @WebMethod
    public boolean deleteEmployee(int id) throws EmployeeNotFound {
        return employeeRepositoryImpl.deleteEmployee(id);
    }

    @WebMethod
    public Employee addEmployee(int id, String name) throws EmployeeAlreadyExists {
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
