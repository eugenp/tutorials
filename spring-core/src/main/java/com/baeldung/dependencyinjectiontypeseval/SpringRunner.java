package com.baeldung.dependencyinjectiontypeseval;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringRunner {

    private static ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

    public static void main(String[] args) {
        ConstructorInjection constructorInjection = getBeanFromJavaConfig(ConstructorInjection.class);
        System.out.println(constructorInjection.echo("Constructor injection example"));

        SetterInjection setterInjection = getBeanFromJavaConfig(SetterInjection.class);
        System.out.println(setterInjection.echo("Setter injection example"));

        FieldInjection fieldInjection = getBeanFromJavaConfig(FieldInjection.class);
        System.out.println(fieldInjection.echo("Field injection example"));
    }

    private static <T> T getBeanFromJavaConfig(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

}
