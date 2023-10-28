package com.baledung.billpugh;

public class EagerLoadedSingleton {
    private static final EagerLoadedSingleton EAGER_LOADED_SINGLETON = new EagerLoadedSingleton();
    private EagerLoadedSingleton() {

    }
    public static EagerLoadedSingleton getInstance() {
        return EAGER_LOADED_SINGLETON;
    }
}
