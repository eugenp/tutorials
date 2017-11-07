package com.baeldung.injectbean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class InjectionRunner {

    public static void main(String[] args) {

        EmployeeService employeeService = getEmployeeService();

        System.out.println("____INJECTED BY CONSTRUCTOR EMPLOYEE____");
        System.out.println(employeeService.getEmploteeName());

        System.out.println("____INJECTED BY SETTER ADDRESS____");
        System.out.println(employeeService.getEmploteeAddress().getStreet());
    }

    private static EmployeeService getEmployeeService() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        return context.getBean(EmployeeService.class);
    }

}