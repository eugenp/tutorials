package com.baeldung.methods;

public class Signature {

    public void logSignature() {
        System.out.println("logSignature()");
    }

    int logSignature(int integer) {
        System.out.println("logSignature(int)");
        return integer;
    }

    protected String logSignature(String string) {
        System.out.println("logSignature(String)");
        return string;
    }

    private Object logSignature(Object object) {
        System.out.println("logSignature(Object)");
        return object;
    }

    public static void main(String ... args) {
        Signature signature = new Signature();
        signature.logSignature();
        signature.logSignature(123);
        signature.logSignature("A String");
        signature.logSignature((Object) "A String casted into an Object");
        signature.logSignature(new Object());
    }
}
