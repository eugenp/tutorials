package com.baeldung.dependencyinjection.injectbyname;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringRunned {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);

        TextEditorSetterInjectionByName injectedByName =
                context.getBean(TextEditorSetterInjectionByName.class);

        System.out.println(injectedByName.getDictionary().hello());
    }
}
