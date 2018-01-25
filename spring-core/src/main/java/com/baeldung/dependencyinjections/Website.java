package com.baeldung.dependencyinjections;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Website {

    @SuppressWarnings("resource")
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        WebsiteControllerConstructorInjection controller = context.getBean(WebsiteControllerConstructorInjection.class);
        System.out.println("The contents of the Website includes : " + controller.getContentsViaConstructorInjection());

        WebsiteControllerSetterInjection controllerwithSetterInjection = (WebsiteControllerSetterInjection) context.getBean("websiteControllerSetterInjection");
        System.out.println("The contents of the Website includes : " + controllerwithSetterInjection.getContentsViaSetterInjection());
    }
}
