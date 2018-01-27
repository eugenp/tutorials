package com.baeldung.beaninjectiontypes.javaconfigexample;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanInjectorJavaConfigExample {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(BeanInjectorConfig.class);
        SimpleBeanConstructorInjection simpleBeanConstructorInjection = (SimpleBeanConstructorInjection) ctx.getBean("simpleBeanConstructorInjection");
        simpleBeanConstructorInjection.doSomething();

        System.out.println("******************");

        SimpleBeanSetterInjection simpleBeanSetterInjection = (SimpleBeanSetterInjection) ctx.getBean("simpleBeanSetterInjection");
        simpleBeanSetterInjection.doSomething();
    }

}
