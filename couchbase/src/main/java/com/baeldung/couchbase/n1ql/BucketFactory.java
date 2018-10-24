package com.baeldung.couchbase.n1ql;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BucketFactory {

    @Autowired
    private Cluster cluster;

    private Bucket travelSampleBucket;
    private Bucket testBucket;

    public Bucket getTravelSampleBucket() {
        return (travelSampleBucket != null) ?
                travelSampleBucket : cluster.openBucket("travel-sample");
    }

    public Bucket getTestBucket() {
        return (testBucket != null) ?
                testBucket : cluster.openBucket("test");
    }
}
