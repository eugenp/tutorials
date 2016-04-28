package com.baeldung.powermockito.introduction;

public class CollaboratorWithStaticMethods {

    public static String firstMethod(String name) {
        return "Hello " + name + " !";
    }

    public static String secondMethod() {
        return "Hello no one!";
    }

    public static String thirdMethod() {
        return "Hello no one again!";
    }
}