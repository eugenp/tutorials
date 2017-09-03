package com.baeldung.jdiffered;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

public class PromiseDemo {

    public static void startJob(String jobName) {

        Deferred<String, String, String> deferred = new DeferredObject<String, String, String>();
        Promise<String, String, String> promise = deferred.promise();

        promise.done((result) -> {
            System.out.println("Job done");
        }).fail((rejection) -> {
            System.out.println("Job fail");
        }).progress((progress) -> {
            System.out.println("Job is in progress");
        }).always((state, result, rejection) -> {
            System.out.println("Job execution started");
        });

        deferred.resolve(jobName);
        // deferred.notify("");
        // deferred.reject("oops");
    }

}