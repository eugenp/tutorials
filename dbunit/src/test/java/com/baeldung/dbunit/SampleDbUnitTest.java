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

public class SampleDbUnitTest extends DBTestCase {
    private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:h2:mem:default;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:schema.sql'";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public SampleDbUnitTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, JDBC_DRIVER);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, JDBC_URL);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, USER);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, PASSWORD);
        // System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "");
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        final InputStream is = SampleDbUnitTest.class.getClassLoader().getResourceAsStream("data.xml");
        return new FlatXmlDataSetBuilder().build(is);
    }

    @Override
    protected DatabaseOperation getSetUpOperation() {
        return DatabaseOperation.REFRESH;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() {
        return DatabaseOperation.NONE;
    }

    @Test
    public void testSelect() throws Exception {
        // Arrange
        final Connection connection = getConnection().getConnection();

        // Act
        final ResultSet rs = connection.createStatement().executeQuery("select * from iTEMS where id = 1");

        // Assert
        assertTrue(rs.next());
        assertEquals("Grey T-Shirt", rs.getString("title"));
    }

    @Test
    public void testDelete() throws Exception {
        // Arrange
        final Connection connection = getConnection().getConnection();

        final InputStream is = SampleDbUnitTest.class.getClassLoader().getResourceAsStream("items_exp_delete.xml");
        ITable expectedTable = (new FlatXmlDataSetBuilder().build(is)).getTable("items");

        // Act
        connection.createStatement().executeUpdate("delete from ITEMS where id = 2");

        // Assert
        final IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("items");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testUpdate() throws Exception {
        // Arrange
        final Connection connection = getConnection().getConnection();

        final InputStream is = SampleDbUnitTest.class.getClassLoader().getResourceAsStream("items_exp_rename.xml");
        ITable expectedTable = (new FlatXmlDataSetBuilder().build(is)).getTable("items");

        // Act
        connection.createStatement().executeUpdate("update ITEMS set title='new name' where id = 1");

        // Assert
        final IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("items");

        Assertion.assertEquals(expectedTable, actualTable);
    }

}
