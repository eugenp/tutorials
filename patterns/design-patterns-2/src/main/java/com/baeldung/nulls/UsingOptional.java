package com.baeldung.nulls;

import java.util.Optional;

public class UsingOptional {

    public Optional<Object> process(boolean processed) {

        String response = doSomething(processed);

        if (response == null) {
            return Optional.empty();
        }

        return Optional.of(response);
    }

    private String doSomething(boolean processed) {

        if (processed) {
            return "passed";
        } else {
            return null;
        }
    }

}
