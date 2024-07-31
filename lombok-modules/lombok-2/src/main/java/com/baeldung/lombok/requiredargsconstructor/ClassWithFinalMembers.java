package com.baeldung.lombok.requiredargsconstructor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClassWithFinalMembers {

    private final String stringObject;

    public String getStringObject() {
        return stringObject;
    }
}
