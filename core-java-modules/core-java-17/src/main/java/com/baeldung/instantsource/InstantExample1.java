package com.baeldung.instantsource;

import java.time.Instant;

public class InstantExample1 {
    InstantWrapper instantWrapper;
    public InstantExample1(InstantWrapper instantWrapper) {
        this.instantWrapper = instantWrapper;
    }

    public Instant getCurrentInstant() {
        return instantWrapper.instant();
    }
}
