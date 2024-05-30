package com.baeldung.quarkus.todos.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TodoEvents {

    @RequiredArgsConstructor
    @Getter
    public class TodoCreated {

        private final Todo todo;

    }

}
