package com.baeldung.couchbase.mapreduce;

interface CouchbaseKeyGenerator<T> {

    String generateKey(T t);
}
