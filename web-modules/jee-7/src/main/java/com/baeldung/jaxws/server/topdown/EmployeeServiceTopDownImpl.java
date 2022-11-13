package com.baeldung.jaxws.server.topdown;

import com.baeldung.jaxws.server.repository.EmployeeRepository;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;


@WebService(name = "EmployeeServiceTopDown", targetNamespace = "http://topdown.server.jaxws.baeldung.com/", endpointInterface = "com.baeldung.jaxws.server.topdown.EmployeeServiceTopDown")
public class EmployeeServiceTopDownImpl implements EmployeeServiceTopDown {

    @Inject private EmployeeRepository employeeRepositoryImpl;

    @WebMethod
    public int countEmployees() {
        return employeeRepositoryImpl.count();
    }
}
