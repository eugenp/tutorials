package com.baeldung.lombok.builder;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Child extends Parent {

    private final String childName;
    private final int childAge;

    @Builder(builderMethodName = "childBuilder")
    public Child(String parentName, int parentAge, String childName, int childAge) {
        super(parentName, parentAge);
        this.childName = childName;
        this.childAge = childAge;
    }

}
