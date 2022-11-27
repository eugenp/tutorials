package com.baeldung.objectcopy;

public class DeepCopy {
    Employee employee;

    public DeepCopy(Employee employee) {
        this.employee = new Employee(employee.getEmployeeId()); // making a deep copy
    }

    public static void main(String[] args) {
        Employee employee1 = new Employee("1");

        DeepCopy deepCopy = new DeepCopy(employee1);
        System.out.println(deepCopy.employee.getEmployeeId()); // [1, 2, 3, 4]
        employee1.setEmployeeId("2"); //modifying the actual input
        System.out.println(deepCopy.employee.getEmployeeId());// still prints [1, 2, 3, 4]
    }
}
