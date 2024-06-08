package com.baeldung.couchbase.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableReactiveCouchbaseRepositories;

import java.util.List;

@Configuration
@EnableReactiveCouchbaseRepositories
public abstract class ReactiveCouchbaseConfiguration extends AbstractCouchbaseConfiguration {

    private CouchbaseProperties couchbaseProperties;

    public ReactiveCouchbaseConfiguration(final CouchbaseProperties couchbaseProperties) {
        this.couchbaseProperties = couchbaseProperties;
    }

    @Override
    public String getConnectionString() {
        return String.join(",", couchbaseProperties.getBootstrapHosts());
    }

    @Override
    public String getUserName() {
        return couchbaseProperties.getBucketName();
    }

    @Override
    public String getPassword() {
        return couchbaseProperties.getBucketPassword();
    }

    @Override
    public String getBucketName() {
        return couchbaseProperties.getBucketName();
    }

}