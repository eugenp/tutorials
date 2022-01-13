package com.baeldung.instantsource;

import java.time.Instant;
import java.time.InstantSource;

public class InstantExample {
    InstantWrapper instantWrapper;
    InstantSource instantSource;

    public InstantExample(InstantWrapper instantWrapper, InstantSource instantSource) {
        this.instantWrapper = instantWrapper;
        this.instantSource = instantSource;
    }

    public Instant getCurrentInstantFromWrapper() {
        return instantWrapper.instant();
    }
    public Instant getCurrentInstantFromInstantSource() {
        return instantSource.instant();
    }
}
