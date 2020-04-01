package com.baeldung.employee.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.employee.data.EmployeeDataAdapter;
import com.baeldung.employee.domain.Employee;
import com.baeldung.employee.dto.EmployeeDTO;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDataAdapter employeeDataAdapter;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void addEmployee(Employee emp) {
        employeeDataAdapter.addEmployee(modelMapper.map(emp, EmployeeDTO.class));
    }

}
