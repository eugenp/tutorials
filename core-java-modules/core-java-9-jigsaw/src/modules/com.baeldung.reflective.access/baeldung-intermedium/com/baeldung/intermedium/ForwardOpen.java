package com.baeldung.intermedium;

public class ForwardOpen {
    public static void addOpens(Class<?> clazz1, Class<?> clazz2) {
        Module currentModule = ForwardOpen.class.getModule();
        Module srcModule = clazz1.getModule();
        Module targetModule = clazz2.getModule();

        System.out.println("current module: " + currentModule.getName());
        System.out.println("source  module: " + srcModule.getName());
        System.out.println("target  module: " + targetModule.getName());

        srcModule.addOpens(clazz1.getPackageName(), targetModule);
    }
}