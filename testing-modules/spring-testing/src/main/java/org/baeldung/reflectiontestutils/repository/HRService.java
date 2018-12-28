package org.baeldung.reflectiontestutils.repository;

import org.springframework.stereotype.Component;

@Component
public class HRService {

    public String getEmployeeStatus(Integer employeeId) {
        return "Inactive";
    }
}
