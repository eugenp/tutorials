package com.baeldung.jaxws;

import com.baeldung.jaxws.model.Employee;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface EmployeeService {

    @WebMethod
    Employee getEmployee(int id);

    @WebMethod
    Employee updateEmployee(int id, String name);

    @WebMethod
    boolean deleteEmployee(int id);

    @WebMethod
    Employee addEmployee(int id, String name);

    @WebMethod
    int countEmployees();

    @WebMethod
    List<Employee> getAllEmployees();
}
