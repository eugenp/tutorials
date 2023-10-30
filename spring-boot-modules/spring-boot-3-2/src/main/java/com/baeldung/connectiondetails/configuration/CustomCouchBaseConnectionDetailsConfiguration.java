package com.baeldung.connectiondetails.configuration;

import org.springframework.boot.autoconfigure.couchbase.CouchbaseConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration(proxyBeanMethods = false)
@Profile("couch")

public class CustomCouchBaseConnectionDetailsConfiguration {
    @Bean
    public CouchbaseConnectionDetails getCouchBaseConnectionDetails() {
        return new CustomCouchBaseConnectionDetails();
    }
}
