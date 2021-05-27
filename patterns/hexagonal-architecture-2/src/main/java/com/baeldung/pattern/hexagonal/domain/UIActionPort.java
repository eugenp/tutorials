package com.baeldung.pattern.hexagonal.domain;

public interface UIActionPort {

    String executeSomeAction(String context);
}
