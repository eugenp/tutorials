package com.baeldung.jdeffered;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

class PromiseDemo {

    static void startJob(String jobName) {

        Deferred<String, String, String> deferred = new DeferredObject<>();
        Promise<String, String, String> promise = deferred.promise();

        promise.done(result -> {}).fail(rejection -> {}).progress(progress -> {}).always((state, result, rejection) -> {});

        deferred.resolve(jobName);
        // deferred.notify("");
        // deferred.reject("oops");
    }

}
