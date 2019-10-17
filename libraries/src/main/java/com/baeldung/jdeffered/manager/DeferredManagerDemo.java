package com.baeldung.jdeffered.manager;

import org.jdeferred.Deferred;
import org.jdeferred.DeferredManager;
import org.jdeferred.Promise;
import org.jdeferred.impl.DefaultDeferredManager;
import org.jdeferred.impl.DeferredObject;

class DeferredManagerDemo {

    public static void initiate() {
        Deferred<String, String, String> deferred = new DeferredObject<>();
        DeferredManager dm = new DefaultDeferredManager();
        Promise<String, String, String> p1 = deferred.promise(), p2 = deferred.promise(), p3 = deferred.promise();
        dm.when(p1, p2, p3).done((result) -> {
            System.out.println("done");
        }).fail((result) -> {
            System.out.println("fail");
        });
        deferred.resolve("Hello Baeldung");
    }
}
