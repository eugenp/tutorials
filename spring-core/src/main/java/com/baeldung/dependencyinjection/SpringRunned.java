package com.baeldung.dependencyinjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringRunned {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);

        TextEditorConstructorInjected constructorInjected = context.getBean(TextEditorConstructorInjected.class);
        TextEditorFieldInjected fieldInjected = context.getBean(TextEditorFieldInjected.class);
        TextEditorSetterInjected setterInjected = context.getBean(TextEditorSetterInjected.class);

        System.out.println(constructorInjected.getDictionary().hello());
        System.out.println(fieldInjected.getDictionary().hello());
        System.out.println(setterInjected.getDictionary().hello());
    }
}
