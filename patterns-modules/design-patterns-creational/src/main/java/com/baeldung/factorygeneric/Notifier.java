package com.baeldung.factoryGeneric;

public interface Notifier<T> {

    void notify(T obj);
}
