package com.baeldung.demeter;

public class Employee {
    private Department department = new Department();

    public Department getDepartment() {
        return department;
    }

    public void submitExpense(Department department, Manager manager, Expenses expenses) {
        manager.approveExpense(expenses);
    }
}