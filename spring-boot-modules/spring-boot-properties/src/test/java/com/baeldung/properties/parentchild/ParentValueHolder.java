package com.baeldung.properties.parentchild;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ParentValueHolder {
    @Value("${parent.name:-}")
    private String parentName;

    @Value("${child.name:-}")
    private String childName;

    public String getParentName() {
        return parentName;
    }

    public String getChildName() {
        return childName;
    }

}
