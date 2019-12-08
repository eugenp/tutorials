package org.baeldung.reflectiontestutils.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeService {
    @Autowired
    private HRService hrService;

    public String findEmployeeStatus(Integer employeeId) {
        return "Employee " + employeeId + " status: " + hrService.getEmployeeStatus(employeeId);
    }
}
