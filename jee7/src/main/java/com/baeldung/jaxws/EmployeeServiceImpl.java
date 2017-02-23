package com.baeldung.jaxws;

import com.baeldung.jaxws.model.Employee;
import com.baeldung.jaxws.model.Result;
import com.baeldung.jaxws.repository.EmployeeRepository;
import com.baeldung.jaxws.repository.EmployeeRepositoryImpl;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService(endpointInterface = "com.baeldung.jaxws.EmployeeService")
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    private EmployeeRepository getRepository() {
        if (null == employeeRepository) {
            employeeRepository = new EmployeeRepositoryImpl();
        }
        return employeeRepository;
    }

    @WebMethod
    public Result getEmployee( int id) {
        try {
            return new Result(true, getRepository().getEmployee(id), null);
        } catch (Exception ex) {
            return new Result(false, null, ex.getMessage());
        }
    }


    @WebMethod
    public Result updateEmployee(Employee employee, int id) throws RuntimeException {
        try {
            return new Result(true, getRepository().updateEmployee(employee, id), null);
        } catch (Exception ex) {
            return new Result(false, null, ex.getMessage());
        }
    }


    @WebMethod
    public Result deleteEmployee(int id) {
        try {
            return new Result(true, getRepository().deleteEmployee(id), null);
        } catch (Exception ex) {
            return new Result(false, null, ex.getMessage());
        }
    }

    @WebMethod
    public Result addEmployee(Employee employee) {

        try {
            return new Result(true, getRepository().addEmployee(new Employee(employee.getId(),
                    employee.getFirstName())), null);
        } catch (Exception ex) {
            return new Result(false, null, ex.getMessage());
        }


    }

    @WebMethod
    public Result countEmployees() {
        return new Result(true, getRepository().count(), null);
    }

    @WebMethod
    public List<Employee> getAllEmployees() {
        return getRepository().getAllEmployees();

    }
}
