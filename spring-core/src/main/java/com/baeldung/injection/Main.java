package com.baeldung.injection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);

        ConstructorDI constructorDI = ctx.getBean(ConstructorDI.class);
        constructorDI.formatMessage();

        SetterDI setterDI = ctx.getBean(SetterDI.class);
        setterDI.formatMessage();

        FieldDI fieldDI = ctx.getBean(FieldDI.class);
        fieldDI.formatMessage();
    }
}
