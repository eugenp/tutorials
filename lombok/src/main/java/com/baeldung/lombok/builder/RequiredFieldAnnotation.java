package com.baeldung.lombok.builder;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder(builderMethodName = "internalBuilder")
@Getter
public class RequiredFieldAnnotation {

    @NonNull
    String name;
    String description;

    public static RequiredFieldAnnotationBuilder builder(String name) {
        return internalBuilder().name(name);
    }
}
