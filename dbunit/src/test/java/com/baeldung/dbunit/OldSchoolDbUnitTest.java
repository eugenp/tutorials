package com.baeldung.dbunit;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OldSchoolDbUnitTest {
    private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:h2:mem:default;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:schema.sql'";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private static IDatabaseTester tester = null;

    @BeforeClass
    public static void setUp() throws Exception {
        tester = initDatabaseTester();
    }

    private static IDatabaseTester initDatabaseTester() throws Exception {
        final JdbcDatabaseTester tester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
        tester.setDataSet(initDataSet());
        tester.setSetUpOperation(DatabaseOperation.REFRESH);
        tester.setTearDownOperation(DatabaseOperation.NONE);
        return tester;
    }

    private static IDataSet initDataSet() throws Exception {
        final InputStream is = OldSchoolDbUnitTest.class.getClassLoader().getResourceAsStream("data.xml");
        return new FlatXmlDataSet(is);
    }

    @Before
    public void setup() throws Exception {
        tester.onSetup();
    }

    @After
    public void tearDown() throws Exception {
        tester.onTearDown();
    }

    @Test
    public void testSelect() throws Exception {
        // Arrange
        final Connection connection = tester.getConnection().getConnection();

        // Act
        final ResultSet rs = connection.createStatement().executeQuery("select * from iTEMS where id = 1");

        // Assert
        assertTrue(rs.next());
        assertEquals("Grey T-Shirt", rs.getString("title"));
    }

    @Test
    public void testDelete() throws Exception {
        // Arrange
        final Connection connection = tester.getConnection().getConnection();

        final InputStream is = OldSchoolDbUnitTest.class.getClassLoader().getResourceAsStream("items_exp_delete.xml");
        ITable expectedTable = (new FlatXmlDataSet(is)).getTable("items");
        //expectedTable = DefaultColumnFilter.excludedColumnsTable(expectedTable, new String[]{"produced"});

        // Act
        connection.createStatement().executeUpdate("delete from ITEMS where id = 2");

        // Assert
        final IDataSet databaseDataSet = tester.getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("items");
        //actualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"produced"});

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testUpdate() throws Exception {
        // Arrange
        final Connection connection = tester.getConnection().getConnection();

        final InputStream is = OldSchoolDbUnitTest.class.getClassLoader().getResourceAsStream("items_exp_rename.xml");
        ITable expectedTable = (new FlatXmlDataSet(is)).getTable("items");
        //expectedTable = DefaultColumnFilter.excludedColumnsTable(expectedTable, new String[]{"produced"});

        // Act
        connection.createStatement().executeUpdate("update ITEMS set title='new name' where id = 1");

        // Assert
        final IDataSet databaseDataSet = tester.getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("items");
        //actualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"produced"});

        Assertion.assertEquals(expectedTable, actualTable);
    }

}
