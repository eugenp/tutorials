package com.baeldung.disableautowiring.thirdpartylib;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Library {

    public static TestBean createBean() {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestBean.class);
        return context.getBean(TestBean.class);
    }
}
