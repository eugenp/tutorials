package com.baeldung.couchbase.spring.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.couchbase.client.java.Bucket;

@Service
@Qualifier("TutorialBucketService")
public class TutorialBucketService implements BucketService {

    @Autowired
    private ClusterService couchbase;

    private Bucket bucket;

    @PostConstruct
    private void init() {
        bucket = couchbase.openBucket("baeldung-tutorial", "");
    }

    @Override
    public Bucket getBucket() {
        return bucket;
    }
}
