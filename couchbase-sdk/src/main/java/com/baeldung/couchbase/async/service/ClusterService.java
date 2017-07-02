package com.baeldung.couchbase.async.service;

import com.couchbase.client.java.Bucket;

interface ClusterService {

    Bucket openBucket(String name, String password);
}
