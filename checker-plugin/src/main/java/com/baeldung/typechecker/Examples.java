package com.baeldung.typechecker;

import org.checkerframework.checker.nullness.qual.NonNull;

public class Examples {

    public void checkValuesNotNull() {
        @NonNull Object ref = null;
        method(Math.random() > .5 ? null : new Object());
        method2(new Object());
    }

    public void method(@NonNull Object thisShouldNotBeNull) {

    }

    public void method2(@NonNull Object thisShouldNotBeNull) {

    }
}
