package com.baeldung.couchbase.async.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("TutorialBucketService")
public class TutorialBucketService extends AbstractBucketService {

    @PostConstruct
    void init() {
        openBucket();
    }

    @Autowired
    public TutorialBucketService(ClusterService clusterService) {
        super(clusterService);
        openBucket();
    }

    @Override
    protected String getBucketName() {
        return "baeldung-tutorial";
    }

    @Override
    protected String getBucketPassword() {
        return "";
    }
}
