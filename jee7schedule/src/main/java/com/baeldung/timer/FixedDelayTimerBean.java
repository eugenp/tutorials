package com.baeldung.timer;

import javax.ejb.*;

/**
 * Created by  ccristianchiovari on 5/2/16.
 */
@Singleton
public class FixedDelayTimerBean {

    @EJB
    private WorkerBean workerBean;

    @Lock(LockType.READ)
    @Schedule(second = "*/5", minute = "*", hour = "*", persistent = false)
    public void atSchedule() throws InterruptedException {
        workerBean.doTimerWork();
    }

}