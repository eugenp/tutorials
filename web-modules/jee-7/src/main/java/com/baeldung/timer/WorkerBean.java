package com.baeldung.timer;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by cristianchiovari on 5/2/16.
 */
@Singleton
public class WorkerBean {

    private AtomicBoolean busy = new AtomicBoolean(false);

    @Lock(LockType.READ)
    public void doTimerWork() throws InterruptedException {

        System.out.println("Timer method called but not started yet !");

        if (!busy.compareAndSet(false, true)) {
            return;
        }

        try {
            System.out.println("Timer work started");
            Thread.sleep(12000);
            System.out.println("Timer work done");
        } finally {
            busy.set(false);
        }
    }
}