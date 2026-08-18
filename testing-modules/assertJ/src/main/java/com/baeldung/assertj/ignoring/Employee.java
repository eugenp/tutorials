package com.baeldung.assertj.ignoring;

public class Employee {

    public Long id;
    public String name;
    public String department;
    public String workAddress;
    public Double netSalary;
    public Double grossSalary;

    public Employee() {
    }

    public Employee(Long id, String name, String department, String workAddress, Double netSalary, Double grossSalary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.workAddress = workAddress;
        this.netSalary = netSalary;
        this.grossSalary = grossSalary;
    }

    // Optional: Override equals and hashCode for better comparison in some contexts
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Employee employee = (Employee) o;

        if (id != null ? !id.equals(employee.id) : employee.id != null) {
            return false;
        }
        if (name != null ? !name.equals(employee.name) : employee.name != null) {
            return false;
        }
        if (department != null ? !department.equals(employee.department) : employee.department != null) {
            return false;
        }
        return netSalary != null ? netSalary.equals(employee.netSalary) : employee.netSalary == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (workAddress != null ? workAddress.hashCode() : 0);
        result = 31 * result + (netSalary != null ? netSalary.hashCode() : 0);
        return result;
    }
}

