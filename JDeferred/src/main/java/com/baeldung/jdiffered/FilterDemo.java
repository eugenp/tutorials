package com.baeldung.jdiffered;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

public class FilterDemo {

    static String modifiedMsg;
    
    public static String filter(String msg) {
        
        Deferred<String, ?, ?> d = new DeferredObject<String, Object, Object>();
        Promise<String, ?, ?> p = d.promise();
        Promise<String, ?, ?> filtered = p.then((result) -> {
            modifiedMsg = "Hello " + result;
        });

        filtered.done((result) -> {
            System.out.println("filtering done");
        });

        d.resolve(msg);
        return modifiedMsg;
        
    }

}
