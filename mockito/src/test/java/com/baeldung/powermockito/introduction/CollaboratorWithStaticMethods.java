package com.baeldung.powermockito.introduction;

class CollaboratorWithStaticMethods {

    static String firstMethod(String name) {
        return "Hello " + name + " !";
    }

    static String secondMethod() {
        return "Hello no one!";
    }

    static String thirdMethod() {
        return "Hello no one again!";
    }
}