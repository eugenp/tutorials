package com.baeldung.read_only_transactions.mysql.spring;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class RoutingDS extends AbstractRoutingDataSource {

    private DataSource writer;
    private DataSource reader;

    RoutingDS(DataSource writer, DataSource reader) {
        this.reader = reader;
        this.writer = writer;

        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put("writer", writer);
        dataSources.put("reader", reader);

        setTargetDataSources(dataSources);
    }

    @Override protected Object determineCurrentLookupKey() {
        final String dataSourceMode = ReadOnlyContext.isReadOnly() ? "reader" : "writer";

        // Testing data source switch
        //System.out.println("-----------------------------Datasource: "+dataSourceMode+" ---------------------------------");

        return dataSourceMode;
    }

}
