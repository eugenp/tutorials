package com.baeldung.hexagonalarchitecture.domain;

import com.baeldung.hexagonalarchitecture.domain.command.RequestGreeting;

import org.requirementsascode.Model;

import java.util.function.Consumer;

public class UseCaseModel {
        private static final Class<RequestGreeting> requestGreeting = RequestGreeting.class;

        public static Model build(Consumer<RequestGreeting> displaysRandomGreeting) {
                return Model.builder().user(requestGreeting).system(displaysRandomGreeting).build();

        }
}
