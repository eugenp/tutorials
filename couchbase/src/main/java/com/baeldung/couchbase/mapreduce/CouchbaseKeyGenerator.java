package com.baeldung.couchbase.mapreduce;

public interface CouchbaseKeyGenerator<T> {

    String generateKey(T t);
}
