package com.baeldung.couchbase.spring.service;

public interface CrudService<T> {

    void create(T t);

    T read(String id);

    T readFromReplica(String id);

    void update(T t);

    void delete(String id);

    boolean exists(String id);
}
