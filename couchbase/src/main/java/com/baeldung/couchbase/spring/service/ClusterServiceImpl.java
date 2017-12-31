package com.baeldung.couchbase.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

@Service
public class ClusterServiceImpl implements ClusterService {
    private static final Logger logger = LoggerFactory.getLogger(ClusterServiceImpl.class);

    private Cluster cluster;
    private Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        CouchbaseEnvironment env = DefaultCouchbaseEnvironment.create();
        cluster = CouchbaseCluster.create(env, "localhost");
    }

    @Override
    synchronized public Bucket openBucket(String name, String password) {
        if (!buckets.containsKey(name)) {
            Bucket bucket = cluster.openBucket(name, password);
            buckets.put(name, bucket);
        }
        return buckets.get(name);
    }

    @Override
    public List<JsonDocument> getDocuments(Bucket bucket, Iterable<String> keys) {
        List<JsonDocument> docs = new ArrayList<>();
        for (String key : keys) {
            JsonDocument doc = bucket.get(key);
            if (doc != null) {
                docs.add(doc);
            }
        }
        return docs;
    }

    @Override
    public List<JsonDocument> getDocumentsAsync(final AsyncBucket asyncBucket, Iterable<String> keys) {
        Observable<JsonDocument> asyncBulkGet = Observable.from(keys).flatMap(new Func1<String, Observable<JsonDocument>>() {
            public Observable<JsonDocument> call(String key) {
                return asyncBucket.get(key);
            }
        });

        final List<JsonDocument> docs = new ArrayList<>();
        try {
            asyncBulkGet.toBlocking().forEach(new Action1<JsonDocument>() {
                public void call(JsonDocument doc) {
                    docs.add(doc);
                }
            });
        } catch (Exception e) {
            logger.error("Error during bulk get", e);
        }

        return docs;
    }
}
