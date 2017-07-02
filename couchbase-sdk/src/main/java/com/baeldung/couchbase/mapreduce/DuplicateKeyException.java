package com.baeldung.couchbase.mapreduce;

@SuppressWarnings("serial")
class DuplicateKeyException extends Exception {

    public DuplicateKeyException(String s) {
        super(s);
    }

}
