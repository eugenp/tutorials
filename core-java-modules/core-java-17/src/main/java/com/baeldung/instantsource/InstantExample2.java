package com.baeldung.instantsource;

import java.time.Instant;
import java.time.InstantSource;

public class InstantExample2 {
    InstantSource instantSource;

    public InstantExample2(InstantSource instantSource) {
        this.instantSource = instantSource;
    }

    public Instant getCurrentInstant() {
        return instantSource.instant();
    }

}
