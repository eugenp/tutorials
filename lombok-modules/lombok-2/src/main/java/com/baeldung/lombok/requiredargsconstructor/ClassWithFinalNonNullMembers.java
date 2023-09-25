package com.baeldung.lombok.requiredargsconstructor;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClassWithFinalNonNullMembers {

    private final String finalStringObject;
    @NonNull
    private String nonNullStringObject;

    private String nonFinalStringObject;

    public String getFinalStringObject() {
        return finalStringObject;
    }

    public String getNonNullStringObject() {
        return nonNullStringObject;
    }

    public String getNonFinalStringObject() {
        return nonFinalStringObject;
    }
}
