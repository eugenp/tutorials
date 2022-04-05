package com.baeldung.concurrent.threadfactory;

import java.util.concurrent.ThreadFactory;

public class BaeldungThreadFactory implements ThreadFactory {

    private int threadId;
    private String name;

    public BaeldungThreadFactory(String name) {
        threadId = 1;
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, name + "-Thread_" + threadId);
        System.out.println("created new thread with id : " + threadId + " and name : " + t.getName());
        threadId++;
        return t;
    }

}
