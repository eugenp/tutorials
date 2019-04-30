package com.baeldung.types;

import java.util.concurrent.Callable;

public class MyOwnDefer {
    public static void defer(Runnable runnable) throws Exception {
        Defer.defer(new Callable<Void>() {
            @Override
            public Void call() {
                runnable.run();
                return null;
            }
        });
    }
}
