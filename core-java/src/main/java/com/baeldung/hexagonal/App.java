package com.baeldung.hexagonal;

import com.baeldung.hexagonal.service.EmployeePort;
import com.baeldung.hexagonal.service.impl.EmployeeAdapter;

public class App {
    public static void main(String[] args) {
        EmployeePort a = new EmployeeAdapter();

        if (!a.getEmployees()
            .isEmpty()) {
            a.getEmployees()
                .forEach(System.out::println);
        }
    }
}
