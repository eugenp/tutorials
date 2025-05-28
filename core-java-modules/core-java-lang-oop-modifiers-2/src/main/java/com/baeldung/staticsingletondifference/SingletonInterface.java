package com.baeldung.staticsingletondifference;

public interface SingletonInterface {

    public String describeMe();

    public String passOnLocks(MyLock lock);

    public void increment();
}
