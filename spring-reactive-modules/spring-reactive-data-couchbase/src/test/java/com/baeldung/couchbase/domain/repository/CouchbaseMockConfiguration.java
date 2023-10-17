package com.baeldung.couchbase.domain.repository;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.boot.test.context.TestConfiguration;

import com.baeldung.couchbase.configuration.CouchbaseProperties;
import com.couchbase.mock.Bucket;
import com.couchbase.mock.BucketConfiguration;
import com.couchbase.mock.CouchbaseMock;

@TestConfiguration
public class CouchbaseMockConfiguration {

    private CouchbaseMock couchbaseMock;

    public CouchbaseMockConfiguration(final CouchbaseProperties couchbaseProperties) {
        final BucketConfiguration bucketConfiguration = new BucketConfiguration();
        bucketConfiguration.numNodes = 1;
        bucketConfiguration.numReplicas = 1;
        bucketConfiguration.numVBuckets = 1024;
        bucketConfiguration.name = couchbaseProperties.getBucketName();
        bucketConfiguration.type = Bucket.BucketType.COUCHBASE;
        bucketConfiguration.password = couchbaseProperties.getBucketPassword();

        try {
            couchbaseMock = new CouchbaseMock(couchbaseProperties.getPort(), Collections.singletonList(bucketConfiguration));
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    @PostConstruct
    public void postConstruct() {
        try {
            couchbaseMock.start();
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        }
        try {
            couchbaseMock.waitForStartup();
        } catch (final InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    @PreDestroy
    public void preDestroy() {
        couchbaseMock.stop();
    }
}
