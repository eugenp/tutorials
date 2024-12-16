package com.baeldung.getthreadbyname;

public class ThreadFinder {

    public static Thread getThreadByName(String name) {
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            if (thread.getName().equals(name)) {
                return thread;
            }
        }
        return null; // Thread not found
    }

    public static Thread getThreadByThreadGroupAndName(ThreadGroup threadGroup, String name) {
        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);

        for (Thread thread : threads) {
            if (thread != null && thread.getName().equals(name)) {
                return thread;
            }
        }
        return null;
    }

}
