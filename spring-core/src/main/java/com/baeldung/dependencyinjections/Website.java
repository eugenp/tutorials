package com.baeldung.dependencyinjections;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Website {

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("dependencyinjections-context.xml");

        WebsiteControllerConstructorInjection controller = (WebsiteControllerConstructorInjection) context.getBean("websiteControllerConstructorInjection");
        System.out.println("The contents of the Website are : " + controller.getContentsViaConstructorInjection());

        WebsiteControllerSetterInjection controllerwithSetterInjection = (WebsiteControllerSetterInjection) context.getBean("websiteControllerSetterInjection");
        System.out.println("The contents of the Website are : " + controllerwithSetterInjection.getContentsViaSetterInjection());
    }
}
