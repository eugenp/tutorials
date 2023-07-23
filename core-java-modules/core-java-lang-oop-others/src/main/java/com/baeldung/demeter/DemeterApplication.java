package com.baeldung.demeter;

public class DemeterApplication {
    public static void main(String[] args) {
   
        Expenses expenses = new Expenses(100, 10);
        Employee employee = new Employee();
        employee.getDepartment()
          .getManager()
          .approveExpense(expenses);

        Department dept = new Department();
        Manager manager = new Manager();

        employee.submitExpense(dept, manager, expenses);
    }
}
