package com.baeldung.staticsingletondifference;

import java.io.ObjectStreamException;
import java.io.Serializable;

public class SerializableCloneableSingleton implements SingletonInterface, Serializable, Cloneable {

    private static final long serialVersionUID = -1917003064592196223L;

    private int state;

    private SerializableCloneableSingleton() {
    }

    private static class SingletonHolder {
        public static final SerializableCloneableSingleton instance = new SerializableCloneableSingleton();
    }

    public static SerializableCloneableSingleton getInstance() {
        return SingletonHolder.instance;
    }

    @Override
    public String describeMe() {
        throw new UnsupportedOperationException("Not Supported Here");
    }

    @Override
    public String passOnLocks(MyLock lock) {
        throw new UnsupportedOperationException("Not Supported Here");
    }

    @Override
    public void increment() {
        this.state++;
    }

    public int getState() {
        return state;
    }

    private Object readResolve() throws ObjectStreamException {
        return SingletonHolder.instance;
    }

    public Object cloneObject() throws CloneNotSupportedException {
        return this.clone();
    }
}
