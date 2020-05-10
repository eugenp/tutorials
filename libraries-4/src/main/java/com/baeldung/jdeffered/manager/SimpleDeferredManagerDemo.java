package com.baeldung.jdeffered.manager;

import org.jdeferred.DeferredManager;
import org.jdeferred.impl.DefaultDeferredManager;

class SimpleDeferredManagerDemo {

    public static void initiate() {
        DeferredManager dm = new DefaultDeferredManager();
        dm.when(() -> 1).done(r -> System.out.println("done")).fail(Throwable::printStackTrace);
    }
}
