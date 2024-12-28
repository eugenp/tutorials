package com.baeldung.concurrent.getthreadbyname;

public class ThreadFinder {

public static Thread getThreadByName(String name) {
    return Thread.getAllStackTraces()
        .keySet()
        .stream()
        .filter(thread -> thread.getName().equals(name))
        .findFirst()
        .orElse(null); // Return null if thread not found
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
