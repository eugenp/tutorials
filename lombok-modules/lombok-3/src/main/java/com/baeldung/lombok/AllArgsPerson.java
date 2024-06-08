package com.baeldung.lombok;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class AllArgsPerson {
    private int age;
    private final String race;
    @NonNull
    private String name;
    private final String nickname = "unknown";
}