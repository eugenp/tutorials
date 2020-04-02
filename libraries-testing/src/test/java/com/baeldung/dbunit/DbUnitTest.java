package com.baeldung.dbunit;

import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;

import static com.baeldung.dbunit.ConnectionSettings.JDBC_DRIVER;
import static com.baeldung.dbunit.ConnectionSettings.JDBC_URL;
import static com.baeldung.dbunit.ConnectionSettings.PASSWORD;
import static com.baeldung.dbunit.ConnectionSettings.USER;
import static org.assertj.core.api.Assertions.assertThat;

public class DbUnitTest extends DBTestCase {

    public DbUnitTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, JDBC_DRIVER);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, JDBC_URL);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, USER);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, PASSWORD);
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        try (final InputStream is = DbUnitTest.class.getClassLoader().getResourceAsStream("dbunit/data.xml")) {
            return new FlatXmlDataSetBuilder().build(is);
        }
    }

    @Override
    protected DatabaseOperation getSetUpOperation() {
        return DatabaseOperation.REFRESH;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() {
        return DatabaseOperation.DELETE_ALL;
    }

    @Test
    public void testSelect() throws Exception {
        final Connection connection = getConnection().getConnection();

        final ResultSet rs = connection.createStatement().executeQuery("select * from ITEMS where id = 1");

        assertThat(rs.next()).isTrue();
        assertThat(rs.getString("title")).isEqualTo("Grey T-Shirt");
    }

    @Test
    public void testDelete() throws Exception {
        final Connection connection = getConnection().getConnection();

        try (final InputStream is = DbUnitTest.class.getClassLoader().getResourceAsStream("dbunit/items_exp_delete.xml")) {
            ITable expectedTable = (new FlatXmlDataSetBuilder().build(is)).getTable("ITEMS");

            connection.createStatement().executeUpdate("delete from ITEMS where id = 2");

            final IDataSet databaseDataSet = getConnection().createDataSet();
            ITable actualTable = databaseDataSet.getTable("ITEMS");

            Assertion.assertEquals(expectedTable, actualTable);
        }
    }

    @Test
    public void testUpdate() throws Exception {
        final Connection connection = getConnection().getConnection();

        try (final InputStream is = DbUnitTest.class.getClassLoader().getResourceAsStream("dbunit/items_exp_rename.xml")) {
            ITable expectedTable = (new FlatXmlDataSetBuilder().build(is)).getTable("ITEMS");

            connection.createStatement().executeUpdate("update ITEMS set title='new name' where id = 1");

            final IDataSet databaseDataSet = getConnection().createDataSet();
            ITable actualTable = databaseDataSet.getTable("ITEMS");

            Assertion.assertEquals(expectedTable, actualTable);
        }
    }

}
