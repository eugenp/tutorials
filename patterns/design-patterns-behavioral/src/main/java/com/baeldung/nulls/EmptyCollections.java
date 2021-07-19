package com.baeldung.nulls;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmptyCollections {

    public List<String> names() {
        if (userExist()) {
            return Stream.of(readName()).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    private boolean userExist() {
        return false;
    }

    private String readName() {
        return "test";
    }
}
