package com.baeldung.lombok.builder;

import lombok.Builder;
import lombok.NonNull;

@Builder
public class RequiredFieldOverload {
    @NonNull
    private String name;
    private String description;

    public static RequiredFieldOverloadBuilder builder(String name) {
        return new RequiredFieldOverloadBuilder().name(name);
    }

    public void example() {
        RequiredFieldAnnotation.builder("NameField").description("Field Description").build();
    }

}
