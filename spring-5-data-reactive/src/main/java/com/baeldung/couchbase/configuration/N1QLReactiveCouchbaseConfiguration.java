package com.baeldung.couchbase.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.couchbase.repository.config.EnableReactiveCouchbaseRepositories;

@Configuration
@EnableReactiveCouchbaseRepositories("com.baeldung.couchbase.domain.repository.n1ql")
@Primary
public class N1QLReactiveCouchbaseConfiguration extends ReactiveCouchbaseConfiguration {

    public N1QLReactiveCouchbaseConfiguration(CouchbaseProperties couchbaseProperties) {
        super(couchbaseProperties);
    }
}
