package com.baeldung.switchpatterns;

public class SwitchStatement {

    public static void main(String[] args) {
        final String b = "B";
        switch (args[0]) {
            case "A" -> System.out.println("Parameter is A");
            case b -> System.out.println("Parameter is b");
            default -> System.out.println("Parameter is unknown");
        };
    }

}
