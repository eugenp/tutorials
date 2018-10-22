package com.baeldung.jdeffered;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

class FilterDemo {

    private static String modifiedMsg;

    static String filter(String msg) {

        Deferred<String, ?, ?> d = new DeferredObject<>();
        Promise<String, ?, ?> p = d.promise();
        Promise<String, ?, ?> filtered = p.then((result) -> {
            modifiedMsg = "Hello " + result;
        });

        filtered.done(r -> System.out.println("filtering done"));

        d.resolve(msg);
        return modifiedMsg;
    }
}
