package com.baeldung.spring.data.couchbase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.core.mapping.event.ValidatingCouchbaseEventListener;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.couchbase.client.java.query.QueryScanConsistency;

@Configuration
@EnableCouchbaseRepositories(basePackages = { "com.baeldung.spring.data.couchbase" })
public class MyCouchbaseConfig extends AbstractCouchbaseConfiguration {

    public static final String NODE_LIST = "localhost";
    public static final String BUCKET_NAME = "baeldung";
    public static final String BUCKET_USERNAME = "baeldung";
    public static final String BUCKET_PASSWORD = "baeldung";

    @Override
    public String getConnectionString() {
        return NODE_LIST;
    }

    @Override
    public String getUserName() {
        return BUCKET_USERNAME;
    }

    @Override
    public String getPassword() {
        return BUCKET_PASSWORD;
    }

    @Override
    public String getBucketName() {
        return BUCKET_NAME;
    }

    @Override
    public QueryScanConsistency getDefaultConsistency() {
        return QueryScanConsistency.REQUEST_PLUS;
    }

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public ValidatingCouchbaseEventListener validatingCouchbaseEventListener() {
        return new ValidatingCouchbaseEventListener(localValidatorFactoryBean());
    }
}
