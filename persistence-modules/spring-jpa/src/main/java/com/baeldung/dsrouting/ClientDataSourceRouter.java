package com.baeldung.dsrouting;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Returns thread bound client lookup key for current context.
 */
public class ClientDataSourceRouter extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return ClientDatabaseContextHolder.getClientDatabase();
    }
}
