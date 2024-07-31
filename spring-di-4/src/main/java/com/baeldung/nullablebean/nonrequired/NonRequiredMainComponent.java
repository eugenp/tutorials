package com.baeldung.nullablebean.nonrequired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NonRequiredMainComponent {

    @Autowired(required = false)
    private NonRequiredSubComponent subComponent;

    public NonRequiredSubComponent getSubComponent() {
        return subComponent;
    }

    public void setSubComponent(final NonRequiredSubComponent subComponent) {
        this.subComponent = subComponent;
    }
}
