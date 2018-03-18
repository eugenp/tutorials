package com.baeldung.beaninjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    private final Employee employee;
    private Department computerDept;

    @Autowired
    private Department accountsDept;

    @Autowired
    public DepartmentService(Employee employee) {
        super();
        this.employee = employee;
    }

    @Autowired
    public void setComputerDept(Department computerDept) {
        this.computerDept = computerDept;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Department getAccountsDept() {
        return accountsDept;
    }

    public void setAccountsDept(Department accountsDept) {
        this.accountsDept = accountsDept;
    }

    public Department getComputerDept() {
        return computerDept;
    }

    @Override
    public String toString() {
        return "DepartmentService [employee=" + employee + ", computerDept=" + computerDept + ", accountsDept=" + accountsDept + "]";
    }

}
