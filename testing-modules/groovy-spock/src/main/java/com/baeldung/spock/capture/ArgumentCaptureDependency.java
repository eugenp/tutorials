package com.baeldung.spock.capture;

public class ArgumentCaptureDependency {

    public String catchMe(final String input) {
        return "***" + input + "***";
    }

}