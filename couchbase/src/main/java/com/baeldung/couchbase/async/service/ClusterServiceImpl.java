package com.baeldung.couchbase.async.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;

@Service
public class ClusterServiceImpl implements ClusterService {

    private Cluster cluster;
    private Map<String, Bucket> buckets = new ConcurrentHashMap<>();
    
    @Autowired
    public ClusterServiceImpl(Cluster cluster) {
        this.cluster = cluster;
    }

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
}
