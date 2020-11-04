package com.baeldung.nulls;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

public class UsingOptional {

    public static final String DEFAULT_VALUE = "Default Value";

    public Optional<Object> process(boolean processed) {
        String response = doSomething(processed);

        return Optional.ofNullable(response);
    }

    public String findFirst() {
        return getList()
            .stream()
            .findFirst()
            .orElse(DEFAULT_VALUE);
    }


    public Optional<String> findOptionalFirst() {
        return getList()
            .stream()
            .findFirst();
    }

    private List<String> getList() {
        return emptyList();
    }

    public Optional<String> optionalListFirst() {
        return getOptionalList()
            .flatMap(list -> list.stream().findFirst());
    }

    private Optional<List> getOptionalList() {
        return Optional.ofNullable(getList());
    }

    private String doSomething(boolean processed) {
        if (processed) {
            return "passed";
        } else {
            return null;
        }
    }
}
