package com.baeldung.concurrent.notificationforcompletetask;

import java.util.concurrent.FutureTask;

public class AlertingFutureTask extends FutureTask<String> {

    private final CallbackInterface callback;

    public AlertingFutureTask(Runnable runnable, Callback callback) {
        super(runnable, null);
        this.callback = callback;
    }

    @Override
    protected void done() {
        callback.taskDone("task details here");
    }
}
