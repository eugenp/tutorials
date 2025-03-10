package com.baeldung.switchpatterns;

public class PatternMatching {

    public static void main(String[] args) {
        Object o = args[0];
        if (o instanceof String s) {
            System.out.printf("Object is a string %s", s);
        } else if(o instanceof Number n) {
            System.out.printf("Object is a number %n", n);
        }
    }

}
