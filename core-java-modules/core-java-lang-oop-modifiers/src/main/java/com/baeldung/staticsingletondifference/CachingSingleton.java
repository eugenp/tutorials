package com.baeldung.staticsingletondifference;

public class CachingSingleton implements SingletonInterface {

    private CachingSingleton() {
    }

    private static class SingletonHolder {
        public static final CachingSingleton instance = new CachingSingleton();
    }

    public static CachingSingleton getInstance() {
        return SingletonHolder.instance;
    }

    @Override
    public String describeMe() {
        return "Caching Responsibilities";
    }

    @Override
    public String passOnLocks(MyLock lock) {
        return lock.takeLock(1);
    }

    @Override
    public void increment() {
        throw new UnsupportedOperationException("Not Supported Here");
    }
}
