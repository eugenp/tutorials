package com.baeldung.jdiffered.manager;

import org.jdeferred.DeferredManager;
import org.jdeferred.impl.DefaultDeferredManager;

public class SimpleDeferredManagerDemo {

    public static void initiate() {
        DeferredManager dm = new DefaultDeferredManager();
        dm.when(() -> {
            return 1;
        }).done((result) -> {
            System.out.println("done");
        }).fail((e) -> {
            e.printStackTrace();
        });
    }

}
