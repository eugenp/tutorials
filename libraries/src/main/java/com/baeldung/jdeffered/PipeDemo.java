package com.baeldung.jdeffered;

import org.jdeferred.Deferred;
import org.jdeferred.DonePipe;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

public class PipeDemo {

    public static enum Result {
        SUCCESS, FAILURE
    };

    static Result status;

    public static Result validate(int num) {
        Deferred<Integer, ?, ?> d = new DeferredObject<Integer, Object, Object>();
        Promise<Integer, ?, ?> p = d.promise();

        p.then(new DonePipe<Integer, Integer, Exception, Void>() {
            public Deferred<Integer, Exception, Void> pipeDone(Integer result) {
                if (result < 90) {
                    return new DeferredObject<Integer, Exception, Void>().resolve(result);
                } else {
                    return new DeferredObject<Integer, Exception, Void>().reject(new Exception("Unacceptable value"));
                }
            }
        }).done((result) -> {
            status = Result.SUCCESS;
        }).fail((result) -> {
            status = Result.FAILURE;
        });

        d.resolve(num);

        return status;
    }

}
