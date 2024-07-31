package com.baeldung.compiletimeconstants;

public class Annotations {

    private final String deprecatedDate = "20-02-14";
    private final String deprecatedTime = "22:00";

    //@Deprecated(since = deprecatedDate + " " + deprecatedTime) //TODO: Required JDK 9+
    public void deprecatedMethod() {}

}
