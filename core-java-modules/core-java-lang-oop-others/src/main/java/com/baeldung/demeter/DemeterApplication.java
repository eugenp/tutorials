package com.baeldung.demeter;

public class DemeterApplication {
    public static void main(String[] args) {

        Manager manager = new Manager();
        Department department = new Department(manager);
        Employee employee = new Employee(department);
        Expenses expenses = new Expenses(100, 10);
        employee.submitExpense(expenses);

        employee.getDepartment()
          .getManager()
          .approveExpense(expenses);
    }

}
