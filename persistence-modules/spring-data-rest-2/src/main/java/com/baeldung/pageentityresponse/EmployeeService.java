package com.baeldung.pageentityresponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final EmployeeClient employeeClient;

    public EmployeeService(EmployeeClient employeeClient) {
        this.employeeClient = employeeClient;
    }

    public Page<EmployeeDto> getEmployeeData(Pageable pageable) {
        return employeeClient.getEmployeeDataFromExternalAPI(pageable);
    }
}