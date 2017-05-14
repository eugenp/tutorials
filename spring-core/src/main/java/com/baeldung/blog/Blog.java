package com.baeldung.blog;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static java.util.Objects.requireNonNull;

public class Blog {

    public static void main(String[] args) {
        runConstructorInjection(appContext(Config.class));
        runConstructorInjection(appContext("baeldung-blog.xml"));
        runSetterInjection(appContext(Config.class));
        runSetterInjection(appContext("baeldung-blog.xml"));
    }

    private static void runConstructorInjection(ApplicationContext context) {
        DateFieldWithConstructorInjection dateField =
            requireNonNull(context).getBean(DateFieldWithConstructorInjection.class);
        System.out.printf("%n[CI] Last modified: %s%n%n", dateField);
    }

    private static void runSetterInjection(ApplicationContext context) {
        DateFieldWithSetterInjection dateField = requireNonNull(context).getBean(DateFieldWithSetterInjection.class);
        System.out.printf("%n[SI] Last modified: %s%n%n", dateField);
    }

    private static ApplicationContext appContext(Class<?> configClass) {
        return new AnnotationConfigApplicationContext(requireNonNull(configClass));
    }

    private static ApplicationContext appContext(String configFile) {
        return new ClassPathXmlApplicationContext(requireNonNull(configFile));
    }
}
