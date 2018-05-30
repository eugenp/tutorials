package com.baeldung.couchbase.async.service;

import java.util.List;

public interface CrudService<T> {

    void create(T t);

    T read(String id);

    T readFromReplica(String id);

    void update(T t);

    void delete(String id);

    List<T> readBulk(Iterable<String> ids);

    void createBulk(Iterable<T> items);

    void updateBulk(Iterable<T> items);

    void deleteBulk(Iterable<String> ids);

    boolean exists(String id);
}
