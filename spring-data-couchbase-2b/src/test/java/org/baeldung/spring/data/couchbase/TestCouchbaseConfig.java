package org.baeldung.spring.data.couchbase;

import org.springframework.data.couchbase.core.convert.MappingCouchbaseConverter;
import org.springframework.data.couchbase.core.query.Consistency;

public class TestCouchbaseConfig extends MyCouchbaseConfig {

    @Override
    public String typeKey() {
        return MappingCouchbaseConverter.TYPEKEY_SYNCGATEWAY_COMPATIBLE;
    }
    
    @Override
    public Consistency getDefaultConsistency() {
        return Consistency.READ_YOUR_OWN_WRITES;
    }
}
