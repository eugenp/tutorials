package com.baeldung.couchbase.async.service;

import com.couchbase.client.java.Bucket;

public abstract class AbstractBucketService implements BucketService {

    private ClusterService clusterService;

    private Bucket bucket;

    void openBucket() {
        bucket = clusterService.openBucket(getBucketName(), getBucketPassword());
    }

    protected abstract String getBucketName();

    protected abstract String getBucketPassword();

    AbstractBucketService(ClusterService clusterService) {
        this.clusterService = clusterService;
    }

    @Override
    public Bucket getBucket() {
        return bucket;
    }
}
