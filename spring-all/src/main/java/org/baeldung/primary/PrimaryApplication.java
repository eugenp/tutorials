package org.baeldung.primary;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class PrimaryApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext(Config.class);

        Employee employee = context.getBean(Employee.class);
        System.out.println(employee);
    }
    
}
