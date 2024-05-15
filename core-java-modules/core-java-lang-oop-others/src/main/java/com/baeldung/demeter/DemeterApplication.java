package com.baeldung.demeter;

public class DemeterApplication {
    public static void main(String[] args) {
   
        Expenses expenses = new Expenses(100, 10);
        Employee employee = new Employee();
        employee.getDepartment()
          .getManager()
          .approveExpense(expenses);

        Manager mgr = new Manager();
        Employee emp = new Employee(mgr);
        emp.submitExpense(expenses);
    }
}
