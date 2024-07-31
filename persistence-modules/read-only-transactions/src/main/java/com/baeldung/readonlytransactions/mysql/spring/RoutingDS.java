package com.baeldung.readonlytransactions.mysql.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

public class RoutingDS extends AbstractRoutingDataSource {

    private static final Logger logger = LoggerFactory.getLogger(RoutingDS.class);

    RoutingDS(DataSource writer, DataSource reader) {

        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put("writer", writer);
        dataSources.put("reader", reader);

        setTargetDataSources(dataSources);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceMode = ReadOnlyContext.isReadOnly() ? "reader" : "writer";

        // Testing data source switch
        logger.debug("-----------------------------Datasource: {} ---------------------------------", dataSourceMode);

        return dataSourceMode;
    }

}
