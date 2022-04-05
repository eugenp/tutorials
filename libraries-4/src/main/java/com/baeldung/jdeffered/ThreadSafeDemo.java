package com.baeldung.jdeffered;

import org.jdeferred.Deferred;
import org.jdeferred.DeferredManager;
import org.jdeferred.Promise;
import org.jdeferred.impl.DefaultDeferredManager;
import org.jdeferred.impl.DeferredObject;

public class ThreadSafeDemo {

    public static void task() {
        DeferredManager dm = new DefaultDeferredManager();
        Deferred<String, String, String> deferred = new DeferredObject<>();
        Promise<String, String, String> p1 = deferred.promise();
        Promise<String, String, String> p = dm.when(p1).done(r -> System.out.println("done")).fail(r -> System.out.println("fail"));

        synchronized (p) {
            while (p.isPending()) {
                try {
                    p.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            p.waitSafely();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        deferred.resolve("Hello Baeldung");
    }

}
