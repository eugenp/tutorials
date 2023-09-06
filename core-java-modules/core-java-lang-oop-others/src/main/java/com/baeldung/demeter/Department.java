package com.baeldung.demeter;

public class Department {

    private Manager manager;

    public Department(Manager manager) {
        this.manager = manager;
    }

    public void approveExpense(Expenses expenses) {
        manager.approveExpense(expenses);
    }

    public Manager getManager() {
        return manager;
    }

}
