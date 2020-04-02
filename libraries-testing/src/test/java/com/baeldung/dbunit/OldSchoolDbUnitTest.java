package com.baeldung.dbunit;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;

import static com.baeldung.dbunit.ConnectionSettings.JDBC_DRIVER;
import static com.baeldung.dbunit.ConnectionSettings.JDBC_URL;
import static com.baeldung.dbunit.ConnectionSettings.PASSWORD;
import static com.baeldung.dbunit.ConnectionSettings.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.dbunit.Assertion.assertEquals;

public class OldSchoolDbUnitTest {

    private static IDatabaseTester tester = null;

    @BeforeClass
    public static void setUp() throws Exception {
        tester = initDatabaseTester();
    }

    private static IDatabaseTester initDatabaseTester() throws Exception {
        final JdbcDatabaseTester tester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
        tester.setDataSet(initDataSet());
        tester.setSetUpOperation(DatabaseOperation.REFRESH);
        tester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        return tester;
    }

    private static IDataSet initDataSet() throws Exception {
        try (final InputStream is = OldSchoolDbUnitTest.class.getClassLoader().getResourceAsStream("dbunit/data.xml")) {
            return new FlatXmlDataSetBuilder().build(is);
        }
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
        final Connection connection = tester.getConnection().getConnection();

        final ResultSet rs = connection.createStatement().executeQuery("select * from ITEMS where id = 1");

        assertThat(rs.next()).isTrue();
        assertThat(rs.getString("title")).isEqualTo("Grey T-Shirt");
    }

    @Test
    public void testIgnoringProduced() throws Exception {
        final Connection connection = tester.getConnection().getConnection();
        final String[] excludedColumns = { "id", "produced" };
        try (final InputStream is = getClass().getClassLoader().getResourceAsStream("dbunit/expected-ignoring-registered_at.xml")) {
            final IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
            final ITable expectedTable = DefaultColumnFilter.excludedColumnsTable(expectedDataSet.getTable("ITEMS"), excludedColumns);

            connection.createStatement().executeUpdate("INSERT INTO ITEMS (title, price, produced)  VALUES('Necklace', 199.99, now())");

            final IDataSet databaseDataSet = tester.getConnection().createDataSet();
            final ITable actualTable = DefaultColumnFilter.excludedColumnsTable(databaseDataSet.getTable("ITEMS"), excludedColumns);

            Assertion.assertEquals(expectedTable, actualTable);
        }
    }

    @Test
    public void testDelete() throws Exception {
        final Connection connection = tester.getConnection().getConnection();

        try (final InputStream is = OldSchoolDbUnitTest.class.getClassLoader().getResourceAsStream("dbunit/items_exp_delete.xml")) {
            ITable expectedTable = new FlatXmlDataSetBuilder().build(is).getTable("ITEMS");

            connection.createStatement().executeUpdate("delete from ITEMS where id = 2");

            final IDataSet databaseDataSet = tester.getConnection().createDataSet();
            ITable actualTable = databaseDataSet.getTable("ITEMS");

            assertEquals(expectedTable, actualTable);
        }
    }

    @Test
    public void testDeleteWithExcludedColumns() throws Exception {
        final Connection connection = tester.getConnection().getConnection();

        try (final InputStream is = OldSchoolDbUnitTest.class.getClassLoader().getResourceAsStream("dbunit/items_exp_delete_no_produced.xml")) {
            final ITable expectedTable = new FlatXmlDataSetBuilder().build(is).getTable("ITEMS");

            connection.createStatement().executeUpdate("delete from ITEMS where id = 2");

            final IDataSet databaseDataSet = tester.getConnection().createDataSet();
            ITable actualTable = databaseDataSet.getTable("ITEMS");
            actualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[] { "produced" });

            assertEquals(expectedTable, actualTable);
        }
    }

    @Test
    public void testUpdate() throws Exception {
        final Connection connection = tester.getConnection().getConnection();

        try (final InputStream is = OldSchoolDbUnitTest.class.getClassLoader().getResourceAsStream("dbunit/items_exp_rename.xml")) {
            final ITable expectedTable = new FlatXmlDataSetBuilder().build(is).getTable("ITEMS");

            connection.createStatement().executeUpdate("update ITEMS set title='new name' where id = 1");

            final IDataSet databaseDataSet = tester.getConnection().createDataSet();
            ITable actualTable = databaseDataSet.getTable("ITEMS");

            assertEquals(expectedTable, actualTable);
        }
    }

    @Test
    public void testUpdateWithExcludedColumns() throws Exception {
        final Connection connection = tester.getConnection().getConnection();

        try (final InputStream is = OldSchoolDbUnitTest.class.getClassLoader().getResourceAsStream("dbunit/items_exp_rename_no_produced.xml")) {
            ITable expectedTable = new FlatXmlDataSetBuilder().build(is).getTable("ITEMS");

            connection.createStatement().executeUpdate("update ITEMS set title='new name' where id = 1");

            final IDataSet databaseDataSet = tester.getConnection().createDataSet();
            ITable actualTable = databaseDataSet.getTable("ITEMS");
            actualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[] { "produced" });

            assertEquals(expectedTable, actualTable);
        }
    }

}
