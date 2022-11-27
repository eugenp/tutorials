package com.baeldung.objectcopy;

public class ShallowCopy {
    Employee employee;

    public ShallowCopy(Employee employee) {
        this.employee = employee;
    }

    public static void main(String[] args) {
        Employee employee1 = new Employee("1");

        ShallowCopy shallowCopy = new ShallowCopy(employee1);
        System.out.println(shallowCopy.employee.getEmployeeId()); // [1, 2, 3, 4]
        employee1.setEmployeeId("2");
        System.out.println(shallowCopy.employee.getEmployeeId());// prints [1, 2, 3, 4, 5]
    }
}
