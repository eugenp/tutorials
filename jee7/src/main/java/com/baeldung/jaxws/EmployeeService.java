package com.baeldung.jaxws;

import com.baeldung.jaxws.exception.EmployeeAlreadyExists;
import com.baeldung.jaxws.exception.EmployeeNotFound;
import com.baeldung.jaxws.model.Employee;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface EmployeeService {

    @WebMethod
    Employee getEmployee(int id) throws EmployeeNotFound;

    @WebMethod
    Employee updateEmployee(int id, String name) throws EmployeeNotFound;

    @WebMethod
    boolean deleteEmployee(int id) throws EmployeeNotFound;

    @WebMethod
    Employee addEmployee(int id, String name) throws EmployeeAlreadyExists;

    @WebMethod
    int countEmployees();

    @WebMethod
    List<Employee> getAllEmployees();
}
