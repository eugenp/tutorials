package com.baeldung.jaxws.introduction.server;

import com.baeldung.jaxws.introduction.server.model.Employee;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface EmployeeService {

    @WebMethod
    List<Employee> getAllEmployees();

    @WebMethod
    Employee getEmployee(int id);

    @WebMethod
    Employee updateEmployee(Employee employee, int id);

    @WebMethod
    boolean deleteEmployee(int id);

    @WebMethod
    Employee addEmployee(Employee employee);
}
