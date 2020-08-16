package com.baeldung.jdbcmetadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MetadataExtractor {
    private final DatabaseMetaData databaseMetaData;

    public MetadataExtractor(Connection connection) throws SQLException {
        this.databaseMetaData = connection.getMetaData();
        DatabaseMetaData databaseMetaData = connection.getMetaData();
    }

    public void extractTableInfo() throws SQLException {
        ResultSet resultSet = databaseMetaData.getTables(null, null, "CUST%", new String[] { "TABLE" });
        while (resultSet.next()) {
            // Print the names of existing tables
            System.out.println(resultSet.getString("TABLE_NAME"));
            System.out.println(resultSet.getString("REMARKS"));
        }
    }

    public void extractSystemTables() throws SQLException {
        ResultSet resultSet = databaseMetaData.getTables(null, null, null, new String[] { "SYSTEM TABLE" });
        while (resultSet.next()) {
            // Print the names of system tables
            System.out.println(resultSet.getString("TABLE_NAME"));
        }
    }

    public void extractViews() throws SQLException {
        ResultSet resultSet = databaseMetaData.getTables(null, null, null, new String[] { "VIEW" });
        while (resultSet.next()) {
            // Print the names of existing views
            System.out.println(resultSet.getString("TABLE_NAME"));
        }
    }

    public void extractColumnInfo(String tableName) throws SQLException {
        ResultSet columns = databaseMetaData.getColumns(null, null, tableName, null);

        while (columns.next()) {
            String columnName = columns.getString("COLUMN_NAME");
            String columnSize = columns.getString("COLUMN_SIZE");
            String datatype = columns.getString("DATA_TYPE");
            String isNullable = columns.getString("IS_NULLABLE");
            String isAutoIncrement = columns.getString("IS_AUTOINCREMENT");
            System.out.println(String.format("ColumnName: %s, columnSize: %s, datatype: %s, isColumnNullable: %s, isAutoIncrementEnabled: %s", columnName, columnSize, datatype, isNullable, isAutoIncrement));
        }
    }

    public void extractPrimaryKeys(String tableName) throws SQLException {
        ResultSet primaryKeys = databaseMetaData.getPrimaryKeys(null, null, tableName);
        while (primaryKeys.next()) {
            String primaryKeyColumnName = primaryKeys.getString("COLUMN_NAME");
            String primaryKeyName = primaryKeys.getString("PK_NAME");
            System.out.println(String.format("columnName:%s, pkName:%s", primaryKeyColumnName, primaryKeyName));
        }
    }

    public void fun() throws SQLException {

    }

    public void extractForeignKeys(String tableName) throws SQLException {
        ResultSet foreignKeys = databaseMetaData.getImportedKeys(null, null, tableName);
        while (foreignKeys.next()) {
            String pkTableName = foreignKeys.getString("PKTABLE_NAME");
            String fkTableName = foreignKeys.getString("FKTABLE_NAME");
            String pkColumnName = foreignKeys.getString("PKCOLUMN_NAME");
            String fkColumnName = foreignKeys.getString("FKCOLUMN_NAME");
            System.out.println(String.format("pkTableName:%s, fkTableName:%s, pkColumnName:%s, fkColumnName:%s", pkTableName, fkTableName, pkColumnName, fkColumnName));
        }
    }

    public void extractDatabaseInfo() throws SQLException {
        String productName = databaseMetaData.getDatabaseProductName();
        String productVersion = databaseMetaData.getDatabaseProductVersion();

        String driverName = databaseMetaData.getDriverName();
        String driverVersion = databaseMetaData.getDriverVersion();

        System.out.println(String.format("Product name:%s, Product version:%s", productName, productVersion));
        System.out.println(String.format("Driver name:%s, Driver Version:%s", driverName, driverVersion));
    }

    public void extractUserName() throws SQLException {
        String userName = databaseMetaData.getUserName();
        System.out.println(userName);
        ResultSet schemas = databaseMetaData.getSchemas();
        while (schemas.next()) {
            String table_schem = schemas.getString("TABLE_SCHEM");
            String table_catalog = schemas.getString("TABLE_CATALOG");
            System.out.println(String.format("Table_schema:%s, Table_catalog:%s", table_schem, table_catalog));
        }
    }

    public void extractSupportedFeatures() throws SQLException {
        System.out.println("Supports scrollable & Updatable Result Set: " + databaseMetaData.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE));
        System.out.println("Supports Full Outer Joins: " + databaseMetaData.supportsFullOuterJoins());
        System.out.println("Supports Stored Procedures: " + databaseMetaData.supportsStoredProcedures());
        System.out.println("Supports Subqueries in 'EXISTS': " + databaseMetaData.supportsSubqueriesInExists());
        System.out.println("Supports Transactions: " + databaseMetaData.supportsTransactions());
        System.out.println("Supports Core SQL Grammar: " + databaseMetaData.supportsCoreSQLGrammar());
        System.out.println("Supports Batch Updates: " + databaseMetaData.supportsBatchUpdates());
        System.out.println("Supports Column Aliasing: " + databaseMetaData.supportsColumnAliasing());
        System.out.println("Supports Savepoints: " + databaseMetaData.supportsSavepoints());
        System.out.println("Supports Union All: " + databaseMetaData.supportsUnionAll());
        System.out.println("Supports Union: " + databaseMetaData.supportsUnion());
    }
}
