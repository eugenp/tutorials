package com.baeldung.nulls;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

public class UsingOptional {

    public Optional<Object> process(boolean processed) {

        String response = doSomething(processed);

        return Optional.ofNullable(response);
    }

    public String findFirst(){

        String firstElement = getList().stream()
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        return firstElement;
    }

    private List<String> getList() {
        return emptyList();
    }

    private String doSomething(boolean processed) {

        if (processed) {
            return "passed";
        } else {
            return null;
        }
    }

}
