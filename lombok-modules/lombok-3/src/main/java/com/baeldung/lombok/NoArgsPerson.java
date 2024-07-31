package com.baeldung.lombok;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor(force = true)
public class NoArgsPerson {
    private int age;
    private final String race;
    @NonNull
    private String name;
    private final String nickname = "unknown";
}