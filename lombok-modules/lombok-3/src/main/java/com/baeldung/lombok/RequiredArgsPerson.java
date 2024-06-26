package com.baeldung.lombok;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RequiredArgsPerson {
    private int age;
    private final String race;
    @NonNull
    private String name;
    private final String nickname = "unknown";
}