package com.baeldung.differenttypesofbeaninjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringRunner {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("differenttypesofbeaninjection-context.xml");
        BeerService beerService = (BeerService) context.getBean("beerServiceSetterInjectionBean");
        String order = beerService.order();

        System.out.println(order);

        UserService userService = (UserService) context.getBean("userServiceFieldInjectionBean");
        String address = userService.getUserAddress();

        System.out.println(address);

        EmployeeService employeeService = (EmployeeService) context.getBean("employeeServiceConstructorInjectionBean");
        String salaryDetails = employeeService.process();

        System.out.println(salaryDetails);

    }
}
