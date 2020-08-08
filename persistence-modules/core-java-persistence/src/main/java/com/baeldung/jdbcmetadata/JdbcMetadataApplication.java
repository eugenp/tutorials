package com.baeldung.jdbcmetadata;

import org.apache.log4j.Logger;

import java.sql.SQLException;

public class JdbcMetadataApplication {

    private static final Logger LOG = Logger.getLogger(JdbcMetadataApplication.class);

    public static void main(String[] args) {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        databaseConfig.init();
        try {
            MetadataExtractor metadataExtractor = new MetadataExtractor(databaseConfig.getConnection());
            metadataExtractor.extractTableInfo();
            metadataExtractor.extractSystemTables();
            metadataExtractor.extractViews();
            String tableName = "CUSTOMER";
            metadataExtractor.extractColumnInfo(tableName);
            metadataExtractor.extractPrimaryKeys(tableName);
            metadataExtractor.extractForeignKeys("CUST_ADDRESS");
            metadataExtractor.extractDatabaseInfo();
            metadataExtractor.extractUserName();
            metadataExtractor.extractSupportedFeatures();
        } catch (SQLException e) {
            LOG.error("Error while executing SQL statements", e);
        }
    }
}
