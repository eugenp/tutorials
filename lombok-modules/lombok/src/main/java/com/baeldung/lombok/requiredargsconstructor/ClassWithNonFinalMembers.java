package com.baeldung.lombok.requiredargsconstructor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClassWithNonFinalMembers {

    private String stringObject;

    public String getStringObject() {
        return stringObject;
    }
}
