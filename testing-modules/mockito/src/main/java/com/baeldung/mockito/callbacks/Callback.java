package com.baeldung.mockito.callbacks;

public interface Callback<T> {

    void reply(T response);
}
