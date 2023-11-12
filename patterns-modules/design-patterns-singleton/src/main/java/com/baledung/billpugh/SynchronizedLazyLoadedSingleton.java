package com.baledung.billpugh;

public class SynchronizedLazyLoadedSingleton {
    private static SynchronizedLazyLoadedSingleton synchronizedLazyLoadedSingleton;

    private SynchronizedLazyLoadedSingleton() {
    }

    public static synchronized SynchronizedLazyLoadedSingleton getInstance() {
        if (null == synchronizedLazyLoadedSingleton) {
            synchronizedLazyLoadedSingleton = new SynchronizedLazyLoadedSingleton();
        }
        return synchronizedLazyLoadedSingleton;
    }
}