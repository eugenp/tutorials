package com.baeldung.lombok;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "construct")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class SpecialPerson {
    private int age;
    private final String race;
    @NonNull
    private String name;
    private final String nickname = "unknown";
}