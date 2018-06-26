package org.baeldung.mockito.service;

public interface Callback<T> {

    void reply(T response);
}
