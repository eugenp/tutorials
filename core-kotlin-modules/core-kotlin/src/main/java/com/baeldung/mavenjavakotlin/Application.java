package com.baeldung.mavenjavakotlin;

import com.baeldung.mavenjavakotlin.services.JavaService;

public class Application {

    private static final String JAVA = "java";
    private static final String KOTLIN = "kotlin";

    public static void main(String[] args) {
        String language = args[0];
        switch (language) {
            case JAVA:
                new JavaService().sayHello();
                break;
            case KOTLIN:
                new KotlinService().sayHello();
                break;
            default:
                // Do nothing
                break;
        }
    }

}
