package com.baeldung.core.privatemodifier;

public class ExampleClass {

    public static void main(String[] args) {
        Employee employee = new Employee("Bob","ABC123");
        employee.setPrivateId("BCD234");
        System.out.println(employee.getPrivateId());
    }
}
