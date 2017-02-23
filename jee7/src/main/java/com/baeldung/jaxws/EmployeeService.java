package com.baeldung.jaxws;


import com.baeldung.jaxws.model.Employee;
import com.baeldung.jaxws.model.Result;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface EmployeeService {


    @WebMethod
    Result getEmployee(int id);

    @WebMethod
    Result updateEmployee(Employee employee, int id) throws RuntimeException;

    @WebMethod
    Result deleteEmployee(int id);

    @WebMethod
    Result addEmployee(Employee employee);

    @WebMethod
    public Result countEmployees();

    @WebMethod
    List<Employee> getAllEmployees();
}
