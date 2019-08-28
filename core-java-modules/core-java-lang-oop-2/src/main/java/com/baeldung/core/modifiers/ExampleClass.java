package com.baeldung.core.modifiers;

public class ExampleClass {

    public static void main(String[] args) {
        Employee employee = new Employee("Bob","ABC123");
        employee.changeId("BCD234");
        System.out.println(employee.getId());
    }
}
