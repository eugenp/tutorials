package com.baeldung.spring.data.couchbase;

import org.springframework.data.couchbase.core.convert.MappingCouchbaseConverter;

public class CustomTypeKeyCouchbaseConfig extends MyCouchbaseConfig {

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
    public String typeKey() {
        return MappingCouchbaseConverter.TYPEKEY_SYNCGATEWAY_COMPATIBLE;
    }
}
