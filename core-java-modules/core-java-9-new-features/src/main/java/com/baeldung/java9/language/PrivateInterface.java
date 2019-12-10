package com.baeldung.java9.language;

public interface PrivateInterface {

    private static String staticPrivate() {
        return "static private";
    }

    private String instancePrivate() {
        return "instance private";
    }

    public default void check() {
        String result = staticPrivate();
        if (!result.equals("static private"))
            throw new AssertionError("Incorrect result for static private interface method");
        PrivateInterface pvt = new PrivateInterface() {
        };
        result = pvt.instancePrivate();
        if (!result.equals("instance private"))
            throw new AssertionError("Incorrect result for instance private interface method");
    }
}
