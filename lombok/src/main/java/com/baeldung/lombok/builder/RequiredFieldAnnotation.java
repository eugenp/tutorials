package com.baeldung.lombok.builder;

import lombok.Builder;
import lombok.NonNull;

@Builder(builderMethodName = "hiddenBuilder")
public class RequiredFieldAnnotation {
    @NonNull
    private String name;
    private String description;

    public static RequiredFieldAnnotationBuilder builder(String name) {
        return hiddenBuilder().name(name);
    }

    public void example() {
        RequiredFieldAnnotation.builder("NameField").description("Field Description").build();
    }
}
