package com.baeldung.spring.data.couchbase;

import com.couchbase.client.java.query.QueryScanConsistency;

public class ReadYourOwnWritesCouchbaseConfig extends MyCouchbaseConfig {

    @Override
    public QueryScanConsistency getDefaultConsistency() {
        return QueryScanConsistency.REQUEST_PLUS;
    }
}
