package com.baeldung.factorygeneric;

public interface Notifier<T> {

    void notify(T obj);
}
