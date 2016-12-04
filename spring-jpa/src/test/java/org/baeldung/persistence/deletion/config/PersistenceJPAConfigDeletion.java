package org.baeldung.persistence.deletion.config;

import org.baeldung.config.PersistenceJPAConfigL2Cache;

import java.util.Properties;

public class PersistenceJPAConfigDeletion extends PersistenceJPAConfigL2Cache {

    public PersistenceJPAConfigDeletion() {
        super();
    }

    @Override
    protected String[] getPackagesToScan() {
        return new String[] { "org.baeldung.persistence.deletion.model" };
    }
}