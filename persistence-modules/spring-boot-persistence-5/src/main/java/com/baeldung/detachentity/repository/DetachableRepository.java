package com.baeldung.detachentity.repository;

public interface DetachableRepository<T> {
    void detach(T t);
}
