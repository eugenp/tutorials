package com.baeldung.primary;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class PrimaryApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext(Config.class);

        Employee employee = context.getBean(Employee.class);
        System.out.println(employee);

        ManagerService service = context.getBean(ManagerService.class);
        Manager manager = service.getManager();
        System.out.println(manager.getManagerName());


        AnnotationConfigApplicationContext context2
            = new AnnotationConfigApplicationContext(CarConfig.class);

        Car car = context2.getBean(Car.class);
        System.out.println(car.getName());
    }
    
}
