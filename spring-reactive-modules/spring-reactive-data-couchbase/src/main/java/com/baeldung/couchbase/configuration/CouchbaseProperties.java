package com.baeldung.couchbase.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Collections;
import java.util.List;

@Configuration
@PropertySource("classpath:couchbase.properties")
public class CouchbaseProperties {

    private final List<String> bootstrapHosts;
    private final String bucketName;
    private final String bucketPassword;
    private final int port;

    public CouchbaseProperties(@Value("${spring.couchbase.bootstrap-hosts}") final List<String> bootstrapHosts, @Value("${spring.couchbase.bucket.name}") final String bucketName, @Value("${spring.couchbase.bucket.password}") final String bucketPassword,
      @Value("${spring.couchbase.port}") final int port) {
        this.bootstrapHosts = Collections.unmodifiableList(bootstrapHosts);
        this.bucketName = bucketName;
        this.bucketPassword = bucketPassword;
        this.port = port;
    }

    public List<String> getBootstrapHosts() {
        return bootstrapHosts;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getBucketPassword() {
        return bucketPassword;
    }

    public int getPort() {
        return port;
    }
}