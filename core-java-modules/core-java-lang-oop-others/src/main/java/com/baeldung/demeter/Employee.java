package com.baeldung.demeter;

public class Employee {
    private Department department = new Department();
    private Manager manager;

    public Employee() {

    }

    Employee(Manager manager) {
        this.manager = manager;
    }

    public Department getDepartment() {
        return department;
    }

    public void submitExpense(Expenses expenses) {
        manager.approveExpense(expenses);
    }

}