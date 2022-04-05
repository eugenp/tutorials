package com.baeldung.crunch;

import org.apache.crunch.MapFn;

@SuppressWarnings("serial")
public class ToUpperCaseWithCounterFn extends MapFn<String, String> {

    @Override
    public String map(String input) {
        if (input == null) {
            return input;
        } else {
            String output = input.toUpperCase();
            if (!input.equals(output)) {
                increment("UpperCase", "modified");
            }
            return output;
        }
    }
}
