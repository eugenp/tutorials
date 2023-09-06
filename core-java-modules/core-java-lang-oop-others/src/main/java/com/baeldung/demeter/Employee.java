package com.baeldung.demeter;

public class Employee {
    private Department department;

    public Employee(Department department) {
        this.department = department;
    }

    public void submitExpense(Expenses expenses) {
        department.approveExpense(expenses);
    }

    public Department getDepartment() {
        return department;
    }

}