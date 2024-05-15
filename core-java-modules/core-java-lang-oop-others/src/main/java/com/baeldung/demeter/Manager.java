package com.baeldung.demeter;

public class Manager {

    public void approveExpense(Expenses expenses) {
        System.out.println("Expense approved" + expenses.total());
    }

}