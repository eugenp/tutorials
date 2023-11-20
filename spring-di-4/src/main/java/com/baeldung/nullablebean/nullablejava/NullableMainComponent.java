package com.baeldung.nullablebean.nullablejava;

import jakarta.annotation.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NullableMainComponent {

    private NullableSubComponent subComponent;

    public NullableMainComponent(final @Nullable NullableSubComponent subComponent) {
        this.subComponent = subComponent;
    }

    public NullableSubComponent getSubComponent() {
        return subComponent;
    }

    public void setSubComponent(final NullableSubComponent subComponent) {
        this.subComponent = subComponent;
    }
}
