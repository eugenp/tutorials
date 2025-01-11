package com.baeldung.assertj.ignoring;

import java.time.LocalDate;

public class Employee {
    public Long id;
    public String name;
    public String department;
    public String homeAddress;
    public String workAddress;
    public LocalDate dateOfBirth;
    public Double grossSalary;
    public Double netSalary;

    // Optional: Override equals and hashCode for better comparison in some contexts
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Employee employee = (Employee) o;

        if (id != null ? !id.equals(employee.id) : employee.id != null)
            return false;
        if (name != null ? !name.equals(employee.name) : employee.name != null)
            return false;
        if (department != null ? !department.equals(employee.department) : employee.department != null)
            return false;
        if (homeAddress != null ? !homeAddress.equals(employee.homeAddress) : employee.homeAddress != null)
            return false;
        if (workAddress != null ? !workAddress.equals(employee.workAddress) : employee.workAddress != null)
            return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(employee.dateOfBirth) : employee.dateOfBirth != null)
            return false;
        if (grossSalary != null ? !grossSalary.equals(employee.grossSalary) : employee.grossSalary != null)
            return false;
        return netSalary != null ? netSalary.equals(employee.netSalary) : employee.netSalary == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (homeAddress != null ? homeAddress.hashCode() : 0);
        result = 31 * result + (workAddress != null ? workAddress.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (grossSalary != null ? grossSalary.hashCode() : 0);
        result = 31 * result + (netSalary != null ? netSalary.hashCode() : 0);
        return result;
    }
}

