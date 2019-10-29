package com.baeldung.jdeffered;

import org.jdeferred.Deferred;
import org.jdeferred.DonePipe;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

class PipeDemo {

    public enum Result {
        SUCCESS, FAILURE
    };

    private static Result status;

    static Result validate(int num) {
        Deferred<Integer, ?, ?> d = new DeferredObject<>();
        Promise<Integer, ?, ?> p = d.promise();

        p.then((DonePipe<Integer, Integer, Exception, Void>) result -> {
            if (result < 90) {
                return new DeferredObject<Integer, Exception, Void>().resolve(result);
            } else {
                return new DeferredObject<Integer, Exception, Void>().reject(new Exception("Unacceptable value"));
            }
        }).done(r -> status = Result.SUCCESS).fail(r -> status = Result.FAILURE);

        d.resolve(num);

        return status;
    }
}
