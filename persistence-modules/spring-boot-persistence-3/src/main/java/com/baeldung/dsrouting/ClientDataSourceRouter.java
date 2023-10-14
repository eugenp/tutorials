package com.baeldung.dsrouting;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Returns thread bound client lookup key for current context.
 */
public class ClientDataSourceRouter extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return ClientDatabaseContextHolder.getClientDatabase();
    }

    public void initDatasource(DataSource clientADataSource,
                               DataSource clientBDataSource) {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(ClientDatabase.CLIENT_A, clientADataSource);
        dataSourceMap.put(ClientDatabase.CLIENT_A, clientBDataSource);
        this.setTargetDataSources(dataSourceMap);
        this.setDefaultTargetDataSource(clientADataSource);
    }
}
