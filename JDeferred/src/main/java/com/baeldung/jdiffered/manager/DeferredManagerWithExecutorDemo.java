package com.baeldung.jdiffered.manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jdeferred.Deferred;
import org.jdeferred.DeferredManager;
import org.jdeferred.Promise;
import org.jdeferred.impl.DefaultDeferredManager;
import org.jdeferred.impl.DeferredObject;

public class DeferredManagerWithExecutorDemo {

    public static void initiate() {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Deferred<String, String, String> deferred = new DeferredObject<String, String, String>();
        DeferredManager dm = new DefaultDeferredManager(executor);
        Promise<String, String, String> p1 = deferred.promise(), p2 = deferred.promise(), p3 = deferred.promise();
        dm.when(p1, p2, p3).done((result) -> {
            System.out.println("done");
        }).fail((result) -> {
            System.out.println("fail");
        });
        deferred.resolve("done");
    }

}
