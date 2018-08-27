package com.baeldung.crunch;

import org.apache.crunch.MapFn;

public class ToUpperCaseFn extends MapFn<String, String> {

    @Override
    public String map(String input) {
        return input != null ? input.toUpperCase() : input;
    }
}
