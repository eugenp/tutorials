package com.baeldung.couchbase.spring.service;

import java.util.List;

import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;

public interface ClusterService {

    Bucket openBucket(String name, String password);

    List<JsonDocument> getDocuments(Bucket bucket, Iterable<String> keys);

    List<JsonDocument> getDocumentsAsync(AsyncBucket bucket, Iterable<String> keys);

}
