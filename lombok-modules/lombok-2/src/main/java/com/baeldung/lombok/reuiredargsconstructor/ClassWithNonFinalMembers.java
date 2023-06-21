package com.baeldung.lombok.reuiredargsconstructor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClassWithNonFinalMembers {

    private String stringObject;

    public String getStringObject() {
        return stringObject;
    }
}
