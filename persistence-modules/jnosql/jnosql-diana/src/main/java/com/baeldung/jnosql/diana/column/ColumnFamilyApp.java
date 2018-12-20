package com.baeldung.jnosql.diana.column;

import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.jnosql.diana.api.column.*;
import org.jnosql.diana.cassandra.column.CassandraConfiguration;

public class ColumnFamilyApp {

    private static final String KEY_SPACE = "myKeySpace";
    private static final String COLUMN_FAMILY = "books";

    public static void main(String... args) throws Exception {

        EmbeddedCassandraServerHelper.startEmbeddedCassandra();

        ColumnConfiguration configuration = new CassandraConfiguration();
        try(ColumnFamilyManagerFactory entityManagerFactory = configuration.get()) {
            ColumnFamilyManager entityManager = entityManagerFactory.get(KEY_SPACE);
            ColumnEntity columnEntity = ColumnEntity.of(COLUMN_FAMILY);
            Column key = Columns.of("id", 10L);
            Column name = Columns.of("name", "JNoSQL in Acion");
            columnEntity.add(key);
            columnEntity.add(name);
            ColumnEntity saved = entityManager.insert(columnEntity);
            System.out.println(saved);
        }

        EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
        EmbeddedCassandraServerHelper.stopEmbeddedCassandra();
    }

}
