package com.baeldung.persistence.deletion.config;

import com.baeldung.spring.PersistenceJPAConfigL2Cache;

public class PersistenceJPAConfigDeletion extends PersistenceJPAConfigL2Cache {

    public PersistenceJPAConfigDeletion() {
        super();
    }

    @Override
    protected String[] getPackagesToScan() {
        return new String[] { "com.baeldung.persistence.deletion.model", "com.baeldung.persistence.model" };
    }
}