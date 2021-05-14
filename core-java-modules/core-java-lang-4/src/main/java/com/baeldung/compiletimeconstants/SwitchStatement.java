package com.baeldung.compiletimeconstants;

public class SwitchStatement {

    private static final String VALUE_ONE = "value-one";

    public static void main(String[] args) {
        final String valueTwo = "value" + "-" + "two";
        switch (args[0]) {
            case VALUE_ONE:
                break;
            case valueTwo:
                break;
        }
    }

}
