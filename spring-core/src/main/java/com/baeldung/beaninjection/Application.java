package com.baeldung.beaninjection;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext appContext = startApplicationContext();

        UserRegistrationMailService mailService = appContext.getBean(UserRegistrationMailService.class);
//        UserRegistrationMailServiceSetterInjection mailService = appContext.getBean(UserRegistrationMailServiceSetterInjection.class);
        final User registeredUser = new User("big.lebowski@example.com", "Big", "Lebowski");
        mailService.sendRegistrationEmail(registeredUser);
        System.out.println("Email sent to: " + registeredUser.getEmail());
    }

    private static AnnotationConfigApplicationContext startApplicationContext() {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.register(ApplicationConfig.class);
        appContext.refresh();
        return appContext;
    }
}
