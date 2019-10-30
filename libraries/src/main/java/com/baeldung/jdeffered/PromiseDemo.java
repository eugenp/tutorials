package com.baeldung.jdeffered;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

class PromiseDemo {

    static void startJob(String jobName) {

        Deferred<String, String, String> deferred = new DeferredObject<>();
        Promise<String, String, String> promise = deferred.promise();

        promise.done(result -> System.out.println("Job done")).fail(rejection -> System.out.println("Job fail")).progress(progress -> System.out.println("Job is in progress")).always((state, result, rejection) -> System.out.println("Job execution started"));

        deferred.resolve(jobName);
        // deferred.notify("");
        // deferred.reject("oops");
    }

}