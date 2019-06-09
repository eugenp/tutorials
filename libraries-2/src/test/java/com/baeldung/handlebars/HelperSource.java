package com.baeldung.handlebars;

public class HelperSource {

    public String person(Person context) {
        String busyString = context.isBusy() ? "busy" : "available";
        return context.getName() + " - " + busyString;
    }
}
