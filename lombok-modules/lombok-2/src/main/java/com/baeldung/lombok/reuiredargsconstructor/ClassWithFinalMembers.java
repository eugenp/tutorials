package com.baeldung.lombok.reuiredargsconstructor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClassWithFinalMembers {

    private final String stringObject;

    public String getStringObject() {
        return stringObject;
    }
}
