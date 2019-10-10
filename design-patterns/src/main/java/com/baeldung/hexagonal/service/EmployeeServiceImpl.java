package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.OutboundPort;
import com.baeldung.hexagonal.model.Employee;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService{

    private OutboundPort outboundPort;

    public EmployeeServiceImpl(OutboundPort outboundPort){
        this.outboundPort=outboundPort;
    }

    public void createEmployee(Employee employee) {
        outboundPort.createEmployee(employee);

    }

    public List<Employee> getEmployeeList() {
        return outboundPort.getEmployeeList();
    }
}
