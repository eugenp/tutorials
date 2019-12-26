package com.baeldung.handlebars;

public class HelperSource {

    public String isBusy(Person context) {
        String busyString = context.isBusy() ? "busy" : "available";
        return context.getName() + " - " + busyString;
    }
}
