package com.baeldung.concurrent.notificationforcompletetask;

public class RunnableImpl implements Runnable {

    private final Runnable task;

    private final CallbackInterface callback;

    private final String taskDoneMessage;

    public RunnableImpl(Runnable task, CallbackInterface callback, String taskDoneMessage) {
        this.task = task;
        this.callback = callback;
        this.taskDoneMessage = taskDoneMessage;
    }

    public void run() {
        task.run();
        callback.taskDone(taskDoneMessage);
    }

}
