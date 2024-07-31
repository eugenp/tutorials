package com.baeldung.nullablebean;

import org.springframework.stereotype.Component;

@Component
public class MainComponent {

    private SubComponent subComponent;

    public MainComponent(final SubComponent subComponent) {
        this.subComponent = subComponent;
    }

    public SubComponent getSubComponent() {
        return subComponent;
    }

    public void setSubComponent(final SubComponent subComponent) {
        this.subComponent = subComponent;
    }
}
