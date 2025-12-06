package com.baeldung.reactor.convertlistoflux;

public interface Callback<T> {

    void onTrigger(T element);
}