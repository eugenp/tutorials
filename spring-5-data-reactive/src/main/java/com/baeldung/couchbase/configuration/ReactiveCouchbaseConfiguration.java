package com.baeldung.couchbase.configuration;

import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import org.springframework.data.couchbase.config.AbstractReactiveCouchbaseConfiguration;

import java.util.List;

public abstract class ReactiveCouchbaseConfiguration extends AbstractReactiveCouchbaseConfiguration {

    private CouchbaseProperties couchbaseProperties;

    public ReactiveCouchbaseConfiguration(final CouchbaseProperties couchbaseProperties) {
        this.couchbaseProperties = couchbaseProperties;
    }

    @Override
    protected List<String> getBootstrapHosts() {
        return couchbaseProperties.getBootstrapHosts();
    }

    @Override
    protected String getBucketName() {
        return couchbaseProperties.getBucketName();
    }

    @Override
    protected String getBucketPassword() {
        return couchbaseProperties.getBucketPassword();
    }

    @Override
    public CouchbaseEnvironment couchbaseEnvironment() {
        return DefaultCouchbaseEnvironment
          .builder()
          .bootstrapHttpDirectPort(couchbaseProperties.getPort())
          .build();
    }
}
