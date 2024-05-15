package com.baeldung.couchbase.mapreduce;

@SuppressWarnings("serial")
public class DuplicateKeyException extends Exception {

    public DuplicateKeyException(String s) {
        super(s);
    }

}
